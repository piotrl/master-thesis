package net.piotrl.music.aggregation.lastfm.dao;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "lastfm_track")
@Data
public class LastFmTrackData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String api_data;
}
