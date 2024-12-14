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
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    // Test case for saving a user in the repository
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

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals(name, savedUser.getName());
        assertEquals(email, savedUser.getEmail());
    }

    // Test case for finding a user by email - user exists
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

        User foundUser = userRepository.findByEmail(email);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
        assertEquals(name, foundUser.getName());
    }

    // Test case for finding a user by email - user does not exist
    @Test
    @Transactional
    @Rollback
    public void testFindByEmailUserNotFound() {
        String email = "non.existent@example.com";

        User foundUser = userRepository.findByEmail(email);

        assertNull(foundUser);
    }

    // Test case for finding a user by email - user does not exist
    @Test
    @Transactional
    @Rollback
    public void testFindByEmailCaseSensitivity() {
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

        User foundUserUppercase = userRepository.findByEmail("TEST1@EXAMPLE.COM");

        assertNotNull(foundUserUppercase);
        assertEquals(email, foundUserUppercase.getEmail());
    }

    // Test case for ensuring saving a user works after deleting a user
    @Test
    @Transactional
    @Rollback
    public void testDeleteAndSaveUser() {
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

        userRepository.delete(user);

        User newUser = User.builder()
                .name("newName")
                .email(email)
                .password("newPassword")
                .imageUrl("newImageUrl")
                .build();
        User savedUser = userRepository.save(newUser);

        assertNotNull(savedUser);
        assertEquals(email, savedUser.getEmail());
        assertEquals("newName", savedUser.getName());
    }
}
