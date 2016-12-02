package net.piotrl.music.rescuetime.api;

import lombok.Data;

import java.util.List;

@Data
public class RescueTimeResponse {
    private String notes;
    private List<String> row_headers;
    private List<List<String>> rows;
}
