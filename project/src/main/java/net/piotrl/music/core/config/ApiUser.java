package net.piotrl.music.core.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class ApiUser extends User {
    private final long id;

    public ApiUser(long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.id = id;
    }

    public long getId() {
        return id;
    }
}
