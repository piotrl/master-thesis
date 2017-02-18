package net.piotrl.music.modules.lastfm.tag.repository;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "lastfm_tag")
@Data
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;

    public TagEntity() {
    }

    public TagEntity(String name) {
        this.name = name;
    }
}
