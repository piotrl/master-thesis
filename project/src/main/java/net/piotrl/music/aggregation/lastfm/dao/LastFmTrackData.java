package net.piotrl.music.aggregation.lastfm.dao;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "lastfm_track")
@Data
public class LastFmTrackData {
    @Id
    @GeneratedValue
    @Column
    private int id;

    @Column
    private Object api_data;
}
