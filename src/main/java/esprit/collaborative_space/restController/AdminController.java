package esprit.collaborative_space.restController;

import esprit.collaborative_space.entity.Comment;
import esprit.collaborative_space.entity.Post;
import esprit.collaborative_space.service.CommentsServiceImp;
import esprit.collaborative_space.service.PostServiceImp;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {
    @Autowired
    private CommentsServiceImp commentService;
    @Autowired
    private PostServiceImp postService;
    public AdminController(PostServiceImp postService, CommentsServiceImp commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> getPostWithComments(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }




    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);  // Assurez-vous que cette méthode est correctement définie dans le service
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);  // Post non trouvé
        } catch (Exception e) {
            return ResponseEntity.status(500).build();  // Erreur interne
        }
    }
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<Comment> >getCommentsByPostId(@PathVariable Long postId) {
        try {
            List<Comment> comments = commentService.getCommentsByPostId(postId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();  // Erreur interne
        }
    }


    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);  // Assurez-vous que cette méthode est correctement définie dans le service
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);  // Commentaire non trouvé
        } catch (Exception e) {
            return ResponseEntity.status(500).build();  // Erreur interne
        }
    }
    // Endpoint pour récupérer les statistiques
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = new HashMap<>();

        // Statistiques sur les posts et les commentaires
        stats.put("totalPosts", postService.getTotalPosts());  // Nombre total d'articles
        stats.put("totalComments", commentService.getTotalComments());  // Nombre total de commentaires


        return ResponseEntity.ok(stats);
    }








}