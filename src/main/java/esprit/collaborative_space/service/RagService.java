package esprit.collaborative_space.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
@Service
public class RagService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String flaskUrl = "http://localhost:5001/rag-query"; // adjust if needed

    public String getAnswerFromFlask(String query) {
        try {
            JSONObject json = new JSONObject();
            json.put("query", query);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(json.toString(), headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    flaskUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            return response.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to fetch from Flask\"}";
        }
    }
}
