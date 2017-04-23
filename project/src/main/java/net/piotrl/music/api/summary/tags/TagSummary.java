package net.piotrl.music.api.summary.tags;

import lombok.Data;

@Data
public class TagSummary {
    private String name;
    private Integer playedTimes;
    private Double duration;
    private Integer corrupted;
}
