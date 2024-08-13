package app.blog.controller.content;

import app.blog.controller.base.BaseController;
import app.blog.model.GenericResponse;
import app.blog.model.content.Post;
import app.blog.model.content.dto.PostResponse;
import app.blog.model.content.dto.SavePostRequest;
import app.blog.model.user.User;
import app.blog.service.auth.AuthService;
import app.blog.service.content.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController implements BaseController<Post> {
    @Autowired
    private PostService postService;

    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<Map> save(Post post) {
        return null;
    }

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam Map<String, String> requestParams) throws Exception{
        String idParam = requestParams.get("id");
        GenericResponse genericResponse = new GenericResponse();
        Long postID = null;
        List<PostResponse> postList = null;
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

    @PostMapping
    public ResponseEntity<?> savePost(@RequestBody @Valid SavePostRequest savePostRequest) {
        GenericResponse genericResponse = new GenericResponse();
        Post post = new Post();
        String email = "123@gmail.com ";
        User user = (User) authService.loadUserByUsername(email);
        post.setUser(user);
        post.setTitle(savePostRequest.getTitle());
        post.setSummary(savePostRequest.getSummary());
        post.setContent(savePostRequest.getContent());
        postService.save(post);
        genericResponse.setMessage("The post is created successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(genericResponse);

    }
}
