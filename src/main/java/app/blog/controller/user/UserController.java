package app.blog.controller.user;

import app.blog.controller.base.BaseController;
import app.blog.model.ErrorResponse;
import app.blog.model.GenericResponse;
import app.blog.model.user.User;
import app.blog.model.user.dto.GetUserProfileResponse;
import app.blog.model.user.dto.UpdateUserInfoRequest;
import app.blog.service.auth.AuthService;
import app.blog.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController implements BaseController<User> {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Override
    @PostMapping
    public ResponseEntity<Map> save(User user) {
        return null;
    }

    @PutMapping("/profile")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateUserInfoRequest request){
        System.out.println(request.getImageUrl());
        User user = userService.findUserById(request.getId());
        GenericResponse genericResponse = new GenericResponse();

        if(user == null){
            String message = "invalid user id";
            String fieldName = "id";
            String fieldMessage = "The user does not exist.";
            Map<String, String> errors = new HashMap<>();
            errors.put(fieldName, fieldMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(message, errors));
        }

        User foundUser = (User) authService.loadUserByUsername(request.getEmail());
        if(foundUser != null){
            String message = "duplicate email";
            String fieldName = "email";
            String fieldMessage = "Email has been used";
            Map<String, String> errors = new HashMap<>();
            errors.put(fieldName, fieldMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(message, errors));
        }
        user.setEmail(request.getEmail());
        user.setImageUrl(request.getImageUrl());
        user.setUsername(request.getUsername());
        userService.save(user);
        genericResponse.setMessage("success");
        genericResponse.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestParam Map<String, String> requestParams) {
        String idParam = requestParams.get("id");
        GenericResponse genericResponse = new GenericResponse();

        if (idParam == null || idParam.isEmpty()) {
            String message = "invalid user id";
            String fieldName = "id";
            String fieldMessage = "The user id cannot be found in the request";
            Map<String,String> errors = new HashMap<>();
            errors.put(fieldName, fieldMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message,errors));
        }
        try {
            Long userID = Long.valueOf(Integer.valueOf(idParam));
            User user = userService.findUserById(userID);
            GetUserProfileResponse getUserProfileResponse = new GetUserProfileResponse();

            if (user != null) {
                getUserProfileResponse.setEmail(user.getEmail());
                getUserProfileResponse.setImageUrl(user.getImageUrl());
                getUserProfileResponse.setUsername(user.getUsername());
                genericResponse.setMessage("success");
                genericResponse.setData(getUserProfileResponse);
                return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
            } else {
                String message = "invalid user id";
                String fieldName = "id";
                String fieldMessage = "The user does not exist.";
                Map<String, String> errors = new HashMap<>();
                errors.put(fieldName, fieldMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(message, errors));
            }
        }catch (NumberFormatException e){
            String message = "invalid user id";
            String fieldName = "id";
            String fieldMessage = "The user ID must be a valid number";
            Map<String, String> errors = new HashMap<>();
            errors.put(fieldName, fieldMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message, errors));
        }
    }

}
