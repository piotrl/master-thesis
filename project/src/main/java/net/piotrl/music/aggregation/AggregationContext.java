package net.piotrl.music.aggregation;

import lombok.Data;
import net.piotrl.music.lastfm.aggregation.LastfmApiProperties;

@Data
public class AggregationContext {
    private Long accountId;
    private String rescuetimeApiKey;
    private LastfmApiProperties lastfmProperties;
}
