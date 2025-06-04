from flask import Flask, request, jsonify
import os
import base64
import mimetypes
import time
from pathlib import Path

# Groq + LangChain
from groq import Groq
from langchain_groq import ChatGroq
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain.chains.combine_documents import create_stuff_documents_chain
from langchain_core.prompts import ChatPromptTemplate
from langchain.chains import create_retrieval_chain
from langchain_community.vectorstores import FAISS
from langchain_community.document_loaders import PyPDFLoader
from langchain_community.embeddings import HuggingFaceEmbeddings

# ML
import joblib
import numpy as np

# Initialize Flask app
app = Flask(__name__)

# Paths
pdf_path = Path("C:/Users/asus/Downloads/forumfinal/Forumfinal/PIDEV_SPRING/PIDEV_SPRING/BackEnd/MicroServices/ForumFinal/pdfs/Data_Science_Career_Guide.pdf")
UPLOAD_FOLDER = './uploads'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
os.makedirs(UPLOAD_FOLDER, exist_ok=True)

# Load SVM model and vectorizer
svm_model = joblib.load("tuned_svm_model (3).pkl")
tfidf_vectorizer = joblib.load("tfidf_vectorizer.pkl")

# Groq setup
client = Groq(api_key="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")
llm = ChatGroq(groq_api_key="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", model_name="Llama3-8b-8192")

# Prompt template for RAG
prompt = ChatPromptTemplate.from_template("""
Answer the questions based on the provided context only. Please provide the most accurate response based on the question.
<context>
{context}
</context>
Question: {input}
""")

# Vectorization for PDF
def vector_embedding():
    embeddings = HuggingFaceEmbeddings(model_name="sentence-transformers/all-MiniLM-L6-v2")
    loader = PyPDFLoader(pdf_path)
    docs = loader.load()
    if not docs:
        return None
    text_splitter = RecursiveCharacterTextSplitter(chunk_size=1000, chunk_overlap=200)
    final_documents = text_splitter.split_documents(docs[:20])
    vectors = FAISS.from_documents(final_documents, embeddings)
    return vectors

# Helper: Encode image to base64
def encode_image_to_base64(image_path):
    with open(image_path, "rb") as img_file:
        return base64.b64encode(img_file.read()).decode("utf-8")

# Helper: Get MIME type
def get_mime_type(image_path):
    mime_type, _ = mimetypes.guess_type(image_path)
    return mime_type or "image/jpeg"

# Route: Image Detection
@app.route('/detect', methods=['POST'])
def detect_nsfw():
    if 'file' not in request.files:
        return jsonify({"error": "No file part"}), 400
    file = request.files['file']
    if file.filename == '':
        return jsonify({"error": "No selected file"}), 400

    image_path = os.path.join(app.config['UPLOAD_FOLDER'], file.filename)
    file.save(image_path)

    image_b64 = encode_image_to_base64(image_path)
    mime_type = get_mime_type(image_path)
    base64_url = f"data:{mime_type};base64,{image_b64}"

    try:
        response = client.chat.completions.create(
            model="meta-llama/llama-4-scout-17b-16e-instruct",
            temperature=0,
            max_completion_tokens=1024,
            top_p=1,
            stream=False,
            stop=None,
            messages=[
                {
                    "role": "user",
                    "content": [
                        {
                            "type": "text",
                            "text": (
                                "You are a content moderation AI. Your task is to examine the image below and detect "
                                "if it contains any harmful, explicit, violent, or NSFW content (e.g., weapons such as knives, "
                                "guns, kitchen knives, or other dangerous objects, as well as blood, gore, fighting, or nudity). "
                                "If the image contains anything harmful or explicit, respond with 'yes' followed by a brief reason. "
                                "If not, respond 'no'."
                            )
                        },
                        {
                            "type": "image_url",
                            "image_url": {"url": base64_url}
                        }
                    ]
                }
            ]
        )

        detection_result = response.choices[0].message.content
        return jsonify({
            "message": "Image processed successfully",
            "detection_result": detection_result
        })

    except Exception as e:
        return jsonify({"error": str(e)}), 500

# Route: Text Detection (SVM)
@app.route('/detect-text', methods=['POST'])
def detect_text():
    try:
        data = request.get_json()
        text = data.get("text", "").strip()
        if not text:
            return jsonify({"error": "Text input is missing."}), 400

        vectorized_text = tfidf_vectorizer.transform([text])
        prediction = svm_model.predict(vectorized_text)[0]
        label_map = {1: "Harmful", 0: "Safe"}

        return jsonify({
            "message": "Text processed successfully.",
            "prediction": int(prediction),
            "label": label_map[int(prediction)]
        })

    except Exception as e:
        return jsonify({"error": str(e)}), 500

# Route: RAG Query
@app.route('/rag-query', methods=['POST'])
def rag_query():
    try:
        data = request.get_json()
        query = data.get("query", "what are the different job roles of data science?")
        vectors = vector_embedding()
        if not vectors:
            return jsonify({"error": "Failed to process documents."}), 400

        retriever = vectors.as_retriever()
        document_chain = create_stuff_documents_chain(llm, prompt)
        retrieval_chain = create_retrieval_chain(retriever, document_chain)

        response = retrieval_chain.invoke({'input': query})
        return response['answer']

    except Exception as e:
        return jsonify({"error": str(e)}), 500

# Start the server
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001, debug=True)
