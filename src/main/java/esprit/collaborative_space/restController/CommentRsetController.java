package esprit.collaborative_space.restController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import esprit.collaborative_space.entity.Comment;
import esprit.collaborative_space.service.ICommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:4200")  // Allow CORS for specific controller
public class CommentRsetController {

    @Autowired
    private ICommentsService commentsService;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestParam Long postId, @RequestParam String postedBy, @RequestBody String content) {
        try {
            return ResponseEntity.ok(commentsService.createComment(postId, postedBy, content));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        try {
            commentsService.deleteComment(id);
            return ResponseEntity.ok("Commentaire supprimé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne lors de la suppression du commentaire");
        }
    }

    @GetMapping("/comments/{postId}")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId) {
        try {
            return ResponseEntity.ok(commentsService.getCommentsByPostId(postId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
        }
    }

    // New Endpoint to generate the QR Code
    @GetMapping("/qr-code/{postId}")
    public ResponseEntity<byte[]> generateQRCode(@PathVariable Long postId) {
        try {
            // Fetch comments for the given post
            List<Comment> comments = commentsService.getCommentsByPostId(postId);

            // If no comments are found, return an error response
            if (comments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Build a string containing the comment's content and postedBy
            StringBuilder commentDetails = new StringBuilder();
            for (Comment comment : comments) {
                commentDetails.append("Content: ").append(comment.getContent())
                        .append("\nPosted By: ").append(comment.getPostedBy())
                        .append("\n\n");
            }

            // Generate QR Code with comment content
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.MARGIN, 1); // Optional: set margin
            BufferedImage qrImage = generateQRCodeImage(commentDetails.toString(), qrCodeWriter, hints);

            // Convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "PNG", baos);
            byte[] qrCodeImage = baos.toByteArray();

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(qrCodeImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private BufferedImage generateQRCodeImage(String text, QRCodeWriter qrCodeWriter, Hashtable<EncodeHintType, Object> hints) throws Exception {
        int size = 300;
        com.google.zxing.common.BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size, hints);
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF); // Black and white pixels
            }
        }
        return image;
    }
}
