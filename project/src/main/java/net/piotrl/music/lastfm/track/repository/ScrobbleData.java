package net.piotrl.music.lastfm.track.repository;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lastfm_scrobble")
@Data
public class ScrobbleData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private Integer trackId;
    @Column
    private Date playedWhen;
    @Column
    private String api_data;
}
