package app.blog.unitTesting.controllerTests;

import app.blog.config.auth.TokenProvider;
import app.blog.controller.AuthController;
import app.blog.model.user.User;
import app.blog.model.user.dto.SignInRequest;
import app.blog.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserSignInControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }


    @Test
    public void signIn_Success() throws Exception {

        SignInRequest signInRequest = new SignInRequest("test@example.com", "Password1234!");

        // Mock user and authentication details
        User mockUser = new User("test@example.com", "Test User", "Password1234!");
        Authentication mockAuthentication = mock(Authentication.class);
        when(mockAuthentication.getPrincipal()).thenReturn(mockUser);

        // Mock service and token generation
        when(authService.findByEmail(signInRequest.getEmail())).thenReturn(mockUser);
        when(authService.isPasswordMatched(signInRequest.getPassword(), mockUser.getPassword())).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(mockAuthentication);
        when(tokenProvider.generateAccessToken(any(User.class))).thenReturn("mockAccessToken");

        // Perform the request and verify
        mockMvc.perform(post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"password\":\"Password1234!\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully signed in"))
                .andExpect(jsonPath("$.accessToken").value("mockAccessToken"));

        verify(authService, times(1)).findByEmail(signInRequest.getEmail());
    }


    @Test
    public void signIn_InvalidEmail() throws Exception {
        SignInRequest signInRequest = new SignInRequest("invalid@example.com", "Password1234!");

        // Mocking the service layer
        when(authService.findByEmail(signInRequest.getEmail())).thenReturn(null);

        mockMvc.perform(post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"invalid@example.com\", \"password\":\"Password123\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("invalid email"))
                .andExpect(jsonPath("$.errors.email").value("User with this email doesn't exist or the email is invalid"));

        verify(authService, times(1)).findByEmail(signInRequest.getEmail());
    }

    @Test
    public void signIn_InvalidPassword() throws Exception {
        SignInRequest signInRequest = new SignInRequest("test@example.com", "WrongPassword");

        // Mocking the service layer
        User mockUser = new User("test@example.com", "Test User", "encodedPassword");
        when(authService.findByEmail(signInRequest.getEmail())).thenReturn(mockUser);
        when(authService.isPasswordMatched(signInRequest.getPassword(), mockUser.getPassword())).thenReturn(false);

        mockMvc.perform(post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"password\":\"WrongPassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid password"))
                .andExpect(jsonPath("$.errors.password").value("wrong password"));

        verify(authService, times(1)).findByEmail(signInRequest.getEmail());
    }

    @Test
    public void signUp_BadRequest() throws Exception {
        mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // Empty body to simulate bad request
                .andExpect(status().isBadRequest());
    }
}
