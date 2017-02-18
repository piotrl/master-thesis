package net.piotrl.music.modules.lastfm.aggregation;

import lombok.Data;

@Data
public class LastfmApiProperties {
    private final String username;
    private final String apiKey;
    private final String secret;

    public LastfmApiProperties(String username, String apiKey) {
        this.username = username;
        this.apiKey = apiKey;
        this.secret = null;
    }

    public LastfmApiProperties(String username, String apiKey, String secret) {
        this.username = username;
        this.apiKey = apiKey;
        this.secret = secret;
    }
}
