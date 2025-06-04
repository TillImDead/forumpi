package esprit.collaborative_space.service;

import esprit.collaborative_space.entity.PeerUser;
import esprit.collaborative_space.repository.PeerUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeerUserService {
    @Autowired
    private PeerUserRepo userRepository;

    public PeerUser registerUser(String username, String peerId) {
        PeerUser user = new PeerUser();
        user.setUsername(username);
        user.setPeerId(peerId);
        return userRepository.save(user);
    }

    public List<PeerUser> getAllUsers() {
        return userRepository.findAll();
    }
}