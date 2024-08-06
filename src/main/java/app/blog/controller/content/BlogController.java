package app.blog.controller.content;

import app.blog.controller.base.BaseController;
import app.blog.model.content.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/experiences")
public class BlogController implements BaseController<Post> {
    @Override
    public ResponseEntity<Map> save(Post post) {
        return null;
    }

}
