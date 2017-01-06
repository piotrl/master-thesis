package net.piotrl.music.lastfm.tag;

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
}
