package net.piotrl.music.aggregation.lastfm;

public class LastFmAuthProperties {
    private String api_key;
    private String secret;
    private String username;

    public LastFmAuthProperties(String api_key, String secret, String username) {
        this.api_key = api_key;
        this.secret = secret;
        this.username = username;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
