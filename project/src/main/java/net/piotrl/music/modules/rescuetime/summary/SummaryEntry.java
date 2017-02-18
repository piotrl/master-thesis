package net.piotrl.music.modules.rescuetime.summary;

import lombok.Data;

@Data
public class SummaryEntry {
    /**
     * @apiNote Access activity history and summary time data for the authorizing user
     */
    private String time_data;

    /**
     * @apiNote Access how much time the authorizing user spent in specific categories
     */
    private String category_data;

    /**
     * @apiNote Access how much time the authorizing user spent in different productivity levels
     */
    private String productivity_data;

    /**
     * @apiNote Access how much time the authorizing user spent in different productivity levels
     */
    private String alert_data;

    /**
     * @apiNote Access how much time the authorizing user spent in different productivity levels
     */
    private String highlight_data;

    /**
     * @apiNote Access how much time the authorizing user spent in different productivity levels
     */
    private String focustime_data;
}
