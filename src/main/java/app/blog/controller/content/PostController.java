package app.blog.controller.content;

import app.blog.controller.base.BaseController;
import app.blog.model.GenericResponse;
import app.blog.model.content.Post;
import app.blog.service.content.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController implements BaseController<Post> {
    @Autowired
    private PostService postService;
    @Override
    public ResponseEntity<Map> save(Post post) {
        return null;
    }

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam Map<String, String> requestParams) throws Exception{
        String idParam = requestParams.get("id");
        GenericResponse genericResponse = new GenericResponse();
        Long postID = null;
        List<Post> postList = null;
        if(idParam != null){
            postID = Long.valueOf(Integer.valueOf(idParam));
            Optional<Post> post = postService.findById(postID);
            if(post.isPresent()){
                Post foundPost = post.get();
                genericResponse.setMessage("success");
                genericResponse.setData(foundPost);
            }else{
                genericResponse.setMessage("The post does not exist.");
                genericResponse.setData(null);
            }
        }else{
            postList = postService.findAll();
            genericResponse.setMessage("success");
            genericResponse.setData(postList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
    }
}
