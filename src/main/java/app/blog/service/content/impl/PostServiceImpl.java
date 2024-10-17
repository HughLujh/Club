package app.blog.service.content.impl;

import app.blog.model.content.Post;
import app.blog.model.content.dto.PostResponse;
import app.blog.repository.content.PostRepository;
import app.blog.service.content.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService<Post> {

    @Autowired
    private PostRepository postRepository;

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<PostResponse> findAll(){
        return postRepository.findAllPosts();
    }
}
