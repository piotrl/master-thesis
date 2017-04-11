package net.piotrl.music.api.summary.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MusicActivitySalienceSummary {
    private Date timestamp;
    private Double music;
    private Double activity;
    private Double salience;
}
