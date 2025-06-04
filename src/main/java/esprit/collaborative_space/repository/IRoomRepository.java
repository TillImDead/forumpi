package esprit.collaborative_space.repository;

import esprit.collaborative_space.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoomRepository extends JpaRepository<Room,Long> {
}
