package net.piotrl.music.aggregation.lastfm;

import lombok.Data;

@Data
public class LastFmAuthProperties {
    private final String api_key;
    private final String secret;
    private final String username;
}
