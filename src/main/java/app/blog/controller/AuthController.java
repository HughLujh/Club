package app.blog.controller;

import app.blog.model.GenericResponse;
import app.blog.model.exceptions.DuplicateObjectExceptions;
import app.blog.model.user.User;
import app.blog.model.user.dto.SignUpRequest;
import app.blog.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest data) throws Exception {

        User user =  new User( data.getEmail(),data.getName(), data.getPassword());
        if(authService.isUserExist(user.getEmail())){
            throw new DuplicateObjectExceptions("email has been registered",
                    "invalid request", HttpStatus.BAD_REQUEST,
                    "email");
        }
        authService.save(user);
        String successMessage = "successfully registered as a personal account";

        return ResponseEntity.status(HttpStatus.OK).body((new GenericResponse<User>(successMessage)));

    }
}
