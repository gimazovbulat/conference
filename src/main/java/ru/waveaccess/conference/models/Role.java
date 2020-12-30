package ru.waveaccess.conference.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum Role implements GrantedAuthority {
    PRESENTER("PRESENTER"),
    ADMIN("ADMIN"),
    LISTENER("LISTENER");

    private final String val;

    @Override
    public String getAuthority() {
        return getVal();
    }
}
