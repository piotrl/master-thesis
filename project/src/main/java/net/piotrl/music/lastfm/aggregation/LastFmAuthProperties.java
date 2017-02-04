package net.piotrl.music.lastfm.aggregation;

import lombok.Data;

@Data
public class LastFmAuthProperties {
    private final String username;
    private final String apiKey;
    private final String secret;

    public LastFmAuthProperties(String username, String apiKey) {
        this.username = username;
        this.apiKey = apiKey;
        this.secret = null;
    }

    public LastFmAuthProperties(String username, String apiKey, String secret) {
        this.username = username;
        this.apiKey = apiKey;
        this.secret = secret;
    }
}
