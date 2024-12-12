package app.blog.unitTesting.serviceTests;

import app.blog.model.user.dto.SignUpRequest;
import app.blog.repository.UserRepository;
import app.blog.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class userServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    private static final String TEST_NAME = "test";
    private static final String TEST_EMAIL = "test@example.com";

    private static final String TEST_PASSWORD = "Password1234!";

    private SignUpRequest signUpRequest;

    @BeforeEach
    void setUp() {
        // Create a UserDto object with test data before running the tests
        signUpRequest = signUpRequest.builder().name(TEST_NAME).email(TEST_EMAIL).password(TEST_PASSWORD).build();
    }
}
