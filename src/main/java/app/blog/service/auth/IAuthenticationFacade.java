package app.blog.service.auth;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
