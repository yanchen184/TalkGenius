package com.talkgenius.security;

import com.talkgenius.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * User Principal.
 *
 * <p>Spring Security UserDetails implementation for authentication.
 *
 * @author TalkGenius Team
 * @since 2025-01-14
 */
@Data
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private String id;
    private String email;
    private String password;
    private boolean isPremium;

    /**
     * Create UserPrincipal from User entity.
     *
     * @param user The user entity
     * @return UserPrincipal instance
     */
    public static UserPrincipal create(User user) {
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getIsPremium()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Simple role-based authorities (could be extended)
        String role = isPremium ? "ROLE_PREMIUM" : "ROLE_FREE";
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
