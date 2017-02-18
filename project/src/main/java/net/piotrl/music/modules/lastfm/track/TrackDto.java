package net.piotrl.music.modules.lastfm.track;

import de.umass.lastfm.Track;
import lombok.Data;
import net.piotrl.music.modules.lastfm.track.repository.TrackEntity;

@Data
public class TrackDto {
    // use for playedWhen
    Track scrobble;

    // use for duration, tags
    TrackEntity details;

    Track lastfmTrackDetails;
}
