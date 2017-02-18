package net.piotrl.music.api.raw;

import lombok.Data;

import java.util.Date;

@Data
public class RawScrobbles {
    private long scrobbleId;
    private Date startTime;
    private String trackName;
    private String artistName;
}
