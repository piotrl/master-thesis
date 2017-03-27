package net.piotrl.music.api.summary.dto;

import lombok.Data;

@Data
public class ArtistProductivity extends ProductivityValue<Integer> {
    public String name;
    public double averageProductivity;
    public long playedTime;
}
