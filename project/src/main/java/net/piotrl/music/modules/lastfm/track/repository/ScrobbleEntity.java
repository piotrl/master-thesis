package net.piotrl.music.modules.lastfm.track.repository;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lastfm_scrobble")
@Data
@NoArgsConstructor
public class ScrobbleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private Long trackId;
    @Column
    private Long accountId;
    @Column
    private Date playedWhen;
    @Column
    private String apiData;
}
