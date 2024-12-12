package app.blog.controller;

import app.blog.config.auth.TokenProvider;
import app.blog.model.ErrorResponse;
import app.blog.model.GenericResponse;
import app.blog.model.exceptions.BadRequestException;
import app.blog.model.exceptions.DuplicateObjectExceptions;
import app.blog.model.user.User;
import app.blog.model.user.dto.SignInRequest;
import app.blog.model.user.dto.SignInResponse;
import app.blog.model.user.dto.SignUpRequest;
import app.blog.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenProvider tokenService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody(required = false) @Valid SignUpRequest data) throws Exception {
        if (data == null) {
            throw new BadRequestException("Request body is missing", "invalid request", HttpStatus.BAD_REQUEST,
                    "email, name and password");
        }
        User user =  new User( data.getEmail(),data.getName(), data.getPassword());
        if(authService.isUserExist(user.getEmail())){
            throw new DuplicateObjectExceptions("Email has been registered",
                    "invalid request", HttpStatus.BAD_REQUEST,
                    "email");
        }
        authService.save(user);
        String successMessage = "Successfully registered as a new user";

        return ResponseEntity.status(HttpStatus.OK).body((new GenericResponse<>(successMessage)));

    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody(required = false) @Valid SignInRequest data) throws Exception {
        if (data == null) {
            throw new BadRequestException("Request body is missing", "invalid request", HttpStatus.BAD_REQUEST,
                    "email and password");
        }
        User user = authService.findByEmail(data.getEmail());
        if(user == null){
            String message = "invalid email";
            String fieldName = "email";
            String fieldMessage = "User with this email doesn't exist or the email is invalid";
            Map<String,String> errors = new HashMap<>();
            errors.put(fieldName, fieldMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(message,errors));
        }
        if(authService.isPasswordMatched(data.getPassword(), user.getPassword())){
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
            var authUser = authenticationManager.authenticate(usernamePassword);
            var accessToken = tokenService.generateAccessToken((User) authUser.getPrincipal());
            String signInMessageFormat = "Successfully signed in";
            return ResponseEntity.status(HttpStatus.OK).body(new SignInResponse(signInMessageFormat, accessToken));
        }else{
            String message = "Invalid password";
            String fieldName = "password";
            String fieldMessage = "wrong password";
            Map <String,String> errors = new HashMap<>();
            errors.put(fieldName, fieldMessage);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(message,errors));
        }

    }
}
