package net.piotrl.music.account;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
@Data
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    private transient String password;

    private String role = "ROLE_USER";

    private Instant created;

    public Account(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
