package net.piotrl.music.lastfm.track.repository;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "lastfm_track")
@Data
public class TrackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long artistId;
    @Column
    private String name;
    @Column
    private String mbid;
    @Column
    private int duration;
    @Column
    private String artist;
    @Column
    private String artistMbid;
    @Column
    private String album;
    @Column
    private String albumMbid;
    @Column
    private String url;
    @Column
    private String imageUrlSmall;
    @Column
    private String imageUrlMedium;
    @Column
    private String imageUrlLarge;
    @Column
    private String imageUrlExtraLarge;
}
