package net.piotrl.music.rescuetime.activity.repository;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rescuetime_activity")
public class ActivityCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String name;
}
