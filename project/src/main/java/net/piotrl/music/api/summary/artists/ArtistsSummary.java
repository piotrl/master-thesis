package net.piotrl.music.api.summary.artists;

import lombok.Data;

@Data
public class ArtistsSummary {
    private String name;
    private String imageUrl;
    private Integer playedTimes;
    private Double duration;
}
