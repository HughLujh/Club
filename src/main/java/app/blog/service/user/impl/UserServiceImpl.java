package app.blog.service.user.impl;

import app.blog.model.user.User;
import app.blog.repository.user.UserRepository;
import app.blog.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserByid(id);
    }
}
