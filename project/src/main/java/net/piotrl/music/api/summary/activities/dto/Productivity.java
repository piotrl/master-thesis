package net.piotrl.music.api.summary.activities.dto;

import lombok.Data;

@Data
public class Productivity<T> {
    private T productive;
    private T distraction;
    private T neutral;

    public Productivity(T productive, T distraction, T neutral) {
        this.productive = productive;
        this.distraction = distraction;
        this.neutral = neutral;
    }
}
