package net.piotrl.music.aggregation.repository;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "aggregation")
public class AggregationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private Date startTime;

    @Column
    private Date finishTime;

    @Column(nullable = false)
    private String status;

    @Column
    private String details;
}
