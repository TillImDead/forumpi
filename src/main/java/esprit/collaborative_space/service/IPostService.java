package esprit.collaborative_space.service;

import esprit.collaborative_space.entity.Post;

import java.util.List;

public interface IPostService {

    void deletePost(Long postId);
    String generateSummary(String content);
    Post updatePost(Long postId, Post postDetails);

    Post savePost(Post post);
    List<Post> getAllPosts();
    Post getPostById(Long postId);
    void likePost(Long postId  );
    List<Post> searchByName(String name);
    public long getTotalPosts();

}
