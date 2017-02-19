package net.piotrl.music.modules.lastfm.tag.repository;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "lastfm_tag")
@Data
@NoArgsConstructor
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;

    public TagEntity(String name) {
        this.name = name;
    }
}
