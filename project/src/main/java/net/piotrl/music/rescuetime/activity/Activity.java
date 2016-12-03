package net.piotrl.music.rescuetime.activity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "rescuetime_activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private Date startTime;
    @Column
    private Date endTime;
    @Column
    private Integer spentTime;
    @Column
    private String activityName;
    @Column
    private String categoryName;
    @Column
    private Integer productivity;
}
