package net.piotrl.music.modules.lastfm.tag.repository;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
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

    public TagTrackEntity() {
    }

    public TagTrackEntity(Long tagId, Long trackId) {
        this.tagId = tagId;
        this.trackId = trackId;
    }
}
