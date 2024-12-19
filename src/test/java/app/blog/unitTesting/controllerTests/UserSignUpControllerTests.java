package app.blog.unitTesting.controllerTests;

import app.blog.controller.AuthController;
import app.blog.model.user.User;
import app.blog.model.user.dto.SignUpRequest;
import app.blog.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserSignUpControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void signUp_Success() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("test@example.com", "Test User", "Password1234!");

        // Mocking the service layer
        when(authService.findByEmail(signUpRequest.getEmail())).thenReturn(null);

        mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"name\":\"Test User\", \"password\":\"Password1234!\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully registered as a new user"));

        verify(authService, times(1)).save(any());
    }

    @Test
    public void signUp_EmailAlreadyRegistered() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("test@example.com", "Test User", "Password1234!");

        // Mocking the service layer
        when(authService.findByEmail(signUpRequest.getEmail())).thenReturn(new User());

        mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"name\":\"Test User\", \"password\":\"Password1234!\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("invalid request"));

        verify(authService, times(0)).save(any());
    }

    @Test
    public void signUp_BadRequest() throws Exception {
        mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
