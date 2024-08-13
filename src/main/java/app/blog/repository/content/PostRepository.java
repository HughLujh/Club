package app.blog.repository.content;

import app.blog.model.content.Post;
import app.blog.model.content.dto.PostResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long id);
    @Query("SELECT new app.blog.model.content.dto.PostResponse(p.id, p.createdAt, p.summary, p.content) " +
            "FROM Post p WHERE p.deletedAt IS NULL")
    List<PostResponse> findAllPosts();
}
