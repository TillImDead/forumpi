package esprit.collaborative_space.repository;

import esprit.collaborative_space.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostId(Long postId);
    @Query("SELECT c.post.id, COUNT(c) FROM Comment c GROUP BY c.post.id")
    List<Object[]> getCommentsPerPost();
}
