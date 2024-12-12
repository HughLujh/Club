package app.blog.unitTesting.serviceTests;

import app.blog.model.user.User;
import app.blog.repository.UserRepository;
import app.blog.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class userServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    private static final String TEST_NAME = "testservice1";
    private static final String TEST_EMAIL = "testservice1@example.com";
    private static final String TEST_PASSWORD = "Password1234!";

    private static final String TEST_IMAGEURL = "TestImageUrl";
    private User user;

    @BeforeEach
    void setUp() {
        // Create a UserDto object with test data before running the tests
        user = user.builder().name(TEST_NAME).email(TEST_EMAIL).password(TEST_PASSWORD).imageUrl(TEST_IMAGEURL).build();
    }

    @Test
    public void registerUserSuccess() {
        // Mock the userRepository.findByEmail method to return null,
        // simulating that no user exists with the provided email
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        // Call the registerUser method on the userService with the userDto
        authService.save(user);

        // Verify that the findByEmail method was called exactly once with the given email
        verify(userRepository, times(1)).findByEmail(user.getEmail());

        // Verify that the save method was called exactly once with any User object
        verify(userRepository, times(1)).save(any(User.class));

    }
}
