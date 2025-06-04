package esprit.collaborative_space.repository;

import esprit.collaborative_space.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMessageRepository extends JpaRepository<Message,Long> {
    List<Message> findByRoomId(Long roomId);
}
