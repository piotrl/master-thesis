package net.piotrl.music.lastfm.aggregation;

import de.umass.lastfm.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Component
public class LastFmConnector {

    // TODO: @Value doesn't work in tests
    @Value("${aggregation.lastfm.api_key}")
    private String api_key = "5f062176c25b0c3570a65bca887188f8";

    @Value("${aggregation.lastfm.secure}")
    private String secure = "680127560c4190a9ce1c1785fb82d684";

    @Value("${aggregation.lastfm.username}")
    private String username = "grovman";

    public LastFmConnector() {}

    public LastFmConnector(String username, String api_key) {
        this.username = username;
        this.api_key = api_key;
    }

    public LastFmAuthProperties properties() {
        return new LastFmAuthProperties(api_key, secure, username);
    }

    public User getUserInfo() {
        return User.getInfo(this.username, this.api_key);
    }

    public PaginatedResult<Track> getUserTracksFromLastDay() {
        return User.getRecentTracks(this.username, this.api_key);
    }

    public PaginatedResult<Track> getScrobblesFrom(LocalDateTime dateTime) {
        return getScrobblesFrom(1, dateTime);
    }

    public PaginatedResult<Track> getScrobblesFrom(int page, LocalDateTime dateTime) {
        long unixTimestamp = dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
        return getRecentTracks(this.username, page, unixTimestamp, this.api_key);
    }

    private static PaginatedResult<Track> getRecentTracks(String user, int page, long unixTimestamp, String apiKey) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("user", user);
        params.put("limit", String.valueOf(100));
        params.put("page", String.valueOf(page));
        params.put("from", String.valueOf(unixTimestamp));
        Result result = Caller.getInstance().call("user.getRecentTracks", apiKey, params);
        return ResponseBuilder.buildPaginatedResult(result, Track.class);
    }
}
