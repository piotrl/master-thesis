package net.piotrl.music.api.raw;

import lombok.Data;

import java.util.Date;

@Data
public class RawActivity {
    private Date startTime;
    private int duration;
    private String actionName;
    private String categoryName;
    private int productivityScore;
}
