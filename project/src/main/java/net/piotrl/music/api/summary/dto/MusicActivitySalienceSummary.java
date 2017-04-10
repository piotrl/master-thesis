package net.piotrl.music.api.summary.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MusicActivitySalienceSummary {
    private Date day;
    private double music;
    private double activity;
    private double salience;
}
