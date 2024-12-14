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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    private static final String TEST_NAME = "testservice1";
    private static final String TEST_EMAIL = "testservice1@example.com";
    private static final String TEST_PASSWORD = "Password1234!";
    private static final String TEST_IMAGEURL = "TestImageUrl";
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder().name(TEST_NAME).email(TEST_EMAIL).password(TEST_PASSWORD).imageUrl(TEST_IMAGEURL).build();
    }

    // test save user service
    @Test
    public void testSaveUserSuccess() {
        String encodedPassword = new BCryptPasswordEncoder().encode(TEST_PASSWORD);

        authService.save(testUser);

        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    public void testLoadUserByUsernameShouldReturnUser() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(testUser);

        User foundUser = authService.loadUserByUsername(testUser.getEmail());

        assertNotNull(foundUser);
    }
}
