package net.piotrl.music.lastfm.artist.repository;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "lastfm_artist")
@Data
public class ArtistData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private String mbid;

    @Column
    private String imageUrl;
}
