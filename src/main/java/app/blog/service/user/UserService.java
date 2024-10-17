package app.blog.service.user;

import app.blog.model.user.User;
import app.blog.service.BaseService;

public interface UserService<T> extends BaseService<T> {
    User findUserById(Long id);
}
