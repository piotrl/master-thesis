package net.piotrl.music.modules.lastfm.tag.repository;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@IdClass(TagTrackEntity.class)
@Table(name = "lastfm_tag_track")
public class TagTrackEntity implements Serializable {
    @Id
    @Column(nullable = false)
    private Long tagId;

    @Id
    @Column(nullable = false)
    private Long trackId;

    public TagTrackEntity(Long tagId, Long trackId) {
        this.tagId = tagId;
        this.trackId = trackId;
    }
}
