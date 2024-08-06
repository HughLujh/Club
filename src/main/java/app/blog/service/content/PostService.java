package app.blog.service.content;

import app.blog.model.content.Post;
import app.blog.service.BaseService;

import java.util.List;
import java.util.Optional;

public interface PostService<T> extends BaseService<T> {
    Optional<Post> findById(Long id);
    List<Post> findAll();
}
