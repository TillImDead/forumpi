package esprit.collaborative_space.repository;

import esprit.collaborative_space.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepository  extends JpaRepository<User,Long> {
    List<User> findByName(String Name);
}
