package esprit.collaborative_space.restController;


import esprit.collaborative_space.entity.PeerUser;
import esprit.collaborative_space.service.PeerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class PeerUserController {
    @Autowired
    private PeerUserService userService;

    /** ✅ Register a new user **/
    @PostMapping("/register")
    public ResponseEntity<PeerUser> registerUser(@RequestParam String username, @RequestParam String peerId) {
        try {
            PeerUser peerUser = userService.registerUser(username, peerId);
            return ResponseEntity.ok(peerUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /** ✅ Get all users **/
    @GetMapping("/all")
    public ResponseEntity<List<PeerUser>> getAllUsers() {
        List<PeerUser> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // ✅ Handles empty user list case
        }
        return ResponseEntity.ok(users);
    }
}
