package com.social.security;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Objects;

@Getter
@ToString
public class AuthToken extends UsernamePasswordAuthenticationToken {

    private String token;

    public AuthToken(String token, String username, String password) {
        super(username, password);
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AuthToken authToken = (AuthToken) o;
        return Objects.equals(token, authToken.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), token);
    }
}