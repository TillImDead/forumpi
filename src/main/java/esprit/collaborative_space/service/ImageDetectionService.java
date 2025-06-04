package esprit.collaborative_space.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.core.io.FileSystemResource;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ImageDetectionService {

    @Value("${flask.api.url}") // Example: http://localhost:5001/detect
    private String flaskImageApiUrl;

    @Value("${flask.badword.api.url}") // Example: http://localhost:5001/badword
    private String flaskBadwordApiUrl;

    public String detectImage(String imagePath) {
        try {
            FileSystemResource fileResource = new FileSystemResource(imagePath);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileResource);

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(flaskImageApiUrl, HttpMethod.POST, requestEntity, String.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to process the image.";
        }
    }

    public String detectBadWords(String text) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String jsonPayload = String.format("{\"text\": \"%s\"}", text.replace("\"", "\\\""));

            HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.postForEntity(flaskBadwordApiUrl, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to process text.";
        }
    }
}

