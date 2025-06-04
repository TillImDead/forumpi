package esprit.collaborative_space.restController;

import esprit.collaborative_space.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import esprit.collaborative_space.entity.ApiResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")

public class ReportController {

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/report")
    public ResponseEntity<Object> reportPost(@RequestBody ReportRequest reportRequest) {
        try {
            // Send email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("ajilibilal570@gmail.com");
            message.setSubject("Report on Post");
            message.setText("A report has been sent for Post ID: " + reportRequest.getPostId());
            javaMailSender.send(message);

            // Return a valid JSON response
            return ResponseEntity.ok(new ApiResponse("Report sent successfully!", true));
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Failed to send the report!", false));
        }
    }



    // Create a simple DTO for handling the report request
    public static class ReportRequest {
        private Long postId;

        public Long getPostId() {
            return postId;
        }

        public void setPostId(Long postId) {
            this.postId = postId;
        }
    }
}