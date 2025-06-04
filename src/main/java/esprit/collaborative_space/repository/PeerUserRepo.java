package esprit.collaborative_space.repository;

import esprit.collaborative_space.entity.PeerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PeerUserRepo extends JpaRepository<PeerUser, Long> {
        Optional<PeerUser> findByUsername(String username);
}
