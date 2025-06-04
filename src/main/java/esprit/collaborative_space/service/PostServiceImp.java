package esprit.collaborative_space.service;

import esprit.collaborative_space.entity.Post;
import esprit.collaborative_space.entity.User;
import esprit.collaborative_space.repository.IPostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class PostServiceImp implements IPostService{

    @Autowired
    private IPostRepository postRepository;
    @Override
    public Post savePost(Post post) {
//        User user =
//        post.setUser();
        post.setLikeCount(0);
        post.setViewCount(0);
        post.setDate(new Date());
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Long postId) {
        Optional<Post> optionlPost = postRepository.findById(postId);
        if (optionlPost.isPresent()) {
            Post post = optionlPost.get();
            post.setViewCount(post.getViewCount() + 1);
            return postRepository.save(post);
        }else {
            throw new EntityNotFoundException("Post Not Found");

        }    }

    @Override
    public void likePost(Long postId) {
        Optional<Post> optionlPost = postRepository.findById(postId);
        if (optionlPost.isPresent()) {
            Post post = optionlPost.get();
            post.setLikeCount(post.getLikeCount() + 1);
            postRepository.save(post);
        }
        else {
            throw new EntityNotFoundException("Post Not Found wkith id: " + postId);
        }

    }
    @Override
    public Post updatePost(Long postId, Post postDetails) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (!optionalPost.isPresent()) {
            throw new EntityNotFoundException("Post Not Found");
        }

        Post post = optionalPost.get();
        post.setName(postDetails.getName());
        post.setContent(postDetails.getContent());
        post.setImg(postDetails.getImg());
        post.setTags(postDetails.getTags());
        post.setPostedBy(postDetails.getPostedBy());
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId) {
        if (postRepository.existsById(postId)) {
            try {
                postRepository.deleteById(postId);
            } catch (Exception e) {
                throw new RuntimeException("Erreur lors de la suppression du post", e);
            }
        } else {
            throw new EntityNotFoundException("Post Not Found");
        }


    }

    public String generateSummary(String content) {
        if (content == null || content.trim().isEmpty()) {
            return "Résumé non disponible.";
        }

        System.out.println("Texte original : " + content); // Debugging

        String[] sentences = content.split("(?<=[.!?])\\s+"); // Séparer en phrases
        if (sentences.length == 1) {
            return sentences[0]; // Si une seule phrase, on la retourne directement
        }

        // Compter la fréquence des mots dans le texte
        Map<String, Integer> wordFrequency = new HashMap<>();
        for (String sentence : sentences) {
            for (String word : sentence.split("\\s+")) {
                word = word.toLowerCase().replaceAll("[^a-zA-Z]", ""); // Nettoyage
                if (!word.isEmpty()) {
                    wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                }
            }
        }

        // Trouver la phrase la plus pertinente (qui contient le plus de mots fréquents)
        String bestSentence = Arrays.stream(sentences)
                .max(Comparator.comparingInt(s -> scoreSentence(s, wordFrequency))) // Trier par pertinence
                .orElse(sentences[0]); // Par défaut, prendre la première phrase

        return bestSentence; // Retourner UNE SEULE phrase comme résumé
    }
    private int scoreSentence(String sentence, Map<String, Integer> wordFrequency) {
        return Arrays.stream(sentence.split("\\s+"))
                .map(word -> word.toLowerCase().replaceAll("[^a-zA-Z]", "")) // Nettoyage
                .mapToInt(word -> wordFrequency.getOrDefault(word, 0)) // Score basé sur la fréquence des mots
                .sum();
    }
    @Override
    public List<Post> searchByName(String name) {
        return postRepository.findAllByNameContaining(name);
    }

    public long getTotalPosts() {
        return postRepository.count();  // Compte le nombre total d'articles
    }
}
