package app.blog.unitTesting.repositoryTests;

import app.blog.model.user.User;
import app.blog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class userRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @Rollback
    public void testSaveUser() {
        String name = "test1";
        String email = "test1@example.com";
        String password = "Password1234!";
        String imageUrl = "testUrl1";
        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .imageUrl(imageUrl)
                .build();

        User savedUser = userRepository.save(user);

        // Assert that the retrieved user is not null
        assertNotNull(savedUser);

        // Assert that the retrieved user id is not null
        assertNotNull(savedUser.getId());

        // Assert that the retrieved user's name matches the expected name
        assertEquals(name, savedUser.getName());

        // Assert that the retrieved user's email matches the expected email
        assertEquals(email, savedUser.getEmail());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindByEmailUserFound() {
        String name = "test1";
        String email = "test1@example.com";
        String password = "Password1234!";
        String imageUrl = "testUrl1";
        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .imageUrl(imageUrl)
                .build();

        userRepository.save(user);

        // Retrieve the user from the database using findByEmail
        User foundUser = userRepository.findByEmail(email);

        // Assert that the retrieved user is not null
        assertNotNull(foundUser);

        // Assert that the retrieved user's email matches the expected email
        assertEquals(email, foundUser.getEmail());

        // Assert that the retrieved user's name matches the expected name
        assertEquals(name, foundUser.getName());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindByEmailUserNotFound() {
        // Find an non existent user
        User foundUser = userRepository.findByEmail("non.existent@example.com");

        // Assert that the retrieved user is null
        assertNull(foundUser);
    }
}
