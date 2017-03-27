package net.piotrl.music.api.summary.dto;

import lombok.Data;

@Data
public class ProductivityValue<T extends Number> {
    private T veryUnproductive;
    private T unproductive;
    private T neutral;
    private T productive;
    private T veryProductive;
}
