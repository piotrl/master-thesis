package net.piotrl.music.modules.rescuetime.activity.repository;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "rescuetime_activity")
public class ActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false)
    private Date startTime;

    @Column(nullable = false)
    private Date endTime;

    @Column(nullable = false)
    private Integer spentTime;

    @Column
    private String activityName;

    @Column
    private String categoryName;

    @Column(nullable = false)
    private Integer productivity;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private Long actionId;

    @Column(nullable = false)
    private Long categoryId;
}
