package net.piotrl.music.mocks;


import net.piotrl.music.modules.aggregation.AggregationContext;
import net.piotrl.music.modules.lastfm.aggregation.LastfmApiProperties;

public class AggregationPropertiesMock {
    public static AggregationContext globalContext() {
        AggregationContext aggregationContext = new AggregationContext();
        aggregationContext.setAccountId(1L);
        aggregationContext.setLastfmProperties(lastfmProperties());
        aggregationContext.setRescuetimeApiKey("B63ApHM8bMq5KuWMqkm4oDxvfjw5H6GWO1UdeYSu");

        return aggregationContext;
    }

    public static AggregationContext companyUserContext() {
        AggregationContext aggregationContext = new AggregationContext();
        aggregationContext.setAccountId(3L);
        aggregationContext.setLastfmProperties(lastfmProperties());
        aggregationContext.setRescuetimeApiKey("B63AziAke3KZaBcVjVdPiVlCPJKabOpyd2Mo8o4B");

        return aggregationContext;
    }

    public static LastfmApiProperties lastfmProperties() {
        return new LastfmApiProperties(
                "grovman",
                "5f062176c25b0c3570a65bca887188f8",
                "680127560c4190a9ce1c1785fb82d684"
        );
    }
}
