package net.piotrl.music.account;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "account")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "ROLE_USER";

    @Column(nullable = false)
    private Instant created;

    public Account(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.created = Instant.now();
    }
}
