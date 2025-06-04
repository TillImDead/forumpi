package esprit.collaborative_space.repository;

import esprit.collaborative_space.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByNameContaining(String name);

}
