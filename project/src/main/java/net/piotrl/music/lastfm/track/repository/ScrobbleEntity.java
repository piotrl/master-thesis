package net.piotrl.music.lastfm.track.repository;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lastfm_scrobble")
@Data
public class ScrobbleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private Long trackId;
    @Column
    private Date playedWhen;
    @Column
    private String apiData;
}
