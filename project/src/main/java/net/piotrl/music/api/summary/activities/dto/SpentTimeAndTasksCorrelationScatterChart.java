package net.piotrl.music.api.summary.activities.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SpentTimeAndTasksCorrelationScatterChart {
    private Date period;
    private int productivity;
    private Date activityStarted;
    private int activityTime;
    private int sumTimeIn5min;
    private int sumTimeByPeriod;
    private int tasksIn5min;
    private int tasksInPeriod;
}
