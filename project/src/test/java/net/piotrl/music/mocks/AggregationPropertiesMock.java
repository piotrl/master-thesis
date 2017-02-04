package net.piotrl.music.mocks;


import net.piotrl.music.aggregation.AggregationContext;
import net.piotrl.music.lastfm.aggregation.LastFmAuthProperties;

public class AggregationPropertiesMock {
    public static AggregationContext globalContext() {
        AggregationContext aggregationContext = new AggregationContext();
        aggregationContext.setAccountId(1L);
        aggregationContext.setLastfmProperties(lastfmProperties());
        aggregationContext.setRescuetimeApiKey("B63GlNCu7IsRAFFodBfxqPeJvzAZjOTyWwqKSJFe");

        return aggregationContext;
    }

    public static LastFmAuthProperties lastfmProperties() {
        return new LastFmAuthProperties(
                "grovman",
                "5f062176c25b0c3570a65bca887188f8",
                "680127560c4190a9ce1c1785fb82d684"
        );
    }
}
