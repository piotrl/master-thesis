package net.piotrl.music.rescuetime.api;

import lombok.Builder;

@Builder
public class RescueTimeQueryParameters {
    /**
     * Possible values:
     * - rank: Organized around a calculated value, usually a sum like time spent
     * - interval: Organized around calendar time
     * - member: ???
     */
    private String perspective = "interval";
    private String resolution_time;
    private String restrict_group;

    /**
     * Allows you to preprocess data through different statistical engines.
     * The perspective dictates the main grouping of the data,
     * this provides different aspects of that main grouping.
     *
     * overview: sums statistics for all activities into their top level category
     * category: sums statistics for all activities into their sub category
     * activity: sums statistics for individual applications / web sites / activities
     * productivity: productivity calculation
     * efficiency: efficiency calculation (not applicable in "rank" perspective)
     * document: sums statistics for individual documents and web pages
     */
    private String restrict_kind;

    /**
     * @apiNote name (of category, activity, or overview)
     *
     * The name of a specific overview, category, application or website.
     * For websites, use the domain component only if it starts with "www", eg. "www.nytimes.com" would be "nytimes.com".
     * The easiest way to see what name you should be using is to retrieve a list that contains the name you want,
     * and inspect it for the exact names.
     */
    private String restrict_thing;

    /**
     * Refers to the specific "document" or "activity" we record for the currently active application, if supported.
     * For example, the document name active when using Microsoft Word.
     * Available for most major applications and web sites.
     * Let us know if yours is not.
     */
    private String restrict_thingy;

    /**
     * Format ISO 8601 "YYYY-MM-DD"
     */
    private String restrict_begin;
    private String restrict_end;
}
