package net.piotrl.music.api.summary.activities;

import lombok.Data;

import java.util.Date;

@Data
public class MultitaskingOnProductivity {
    private Date date;
    private Date start_time;
    private Integer productive; // seconds
    private Integer distraction; // seconds
    private Integer activitiesCount;
}
