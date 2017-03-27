package net.piotrl.music.api.summary.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MostPopularArtistsProductivity {
    public ProductivityValue<Double> averagePercentageActivity;
    public ProductivityValue<Double> averageHoursActivity;
    public List<ArtistProductivity> artists;
}
