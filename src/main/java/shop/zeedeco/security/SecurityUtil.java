package shop.zeedeco.security;


import java.util.Optional;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Optional<UserDetailsImpl> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication.getPrincipal() instanceof UserDetailsImpl) {
                        return (UserDetailsImpl) authentication.getPrincipal();
                    }
                    return null;
                });
    }
}
