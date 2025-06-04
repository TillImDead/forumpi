package esprit.collaborative_space.service;

import esprit.collaborative_space.entity.Comment;

import java.util.List;

public interface ICommentsService {

    void deleteComment(Long commentId);
    long getTotalComments();
    Comment createComment(Long postId, String postedBy, String content);
    List<Object[]> getCommentsPerPost();// Vérifiez que le type de retour est long

    List<Comment> getCommentsByPostId(Long postId);
}
