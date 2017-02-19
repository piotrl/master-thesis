package net.piotrl.music.modules.lastfm.artist.repository;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "lastfm_artist")
@Data
@NoArgsConstructor
public class ArtistEntity {

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
