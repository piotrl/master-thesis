package net.piotrl.music.modules.aggregation;

import lombok.Data;
import net.piotrl.music.modules.lastfm.aggregation.LastfmApiProperties;

@Data
public class AggregationContext {
    private Long accountId;
    private String rescuetimeApiKey;
    private LastfmApiProperties lastfmProperties;

    private AggregationResult result = new AggregationResult();
}
