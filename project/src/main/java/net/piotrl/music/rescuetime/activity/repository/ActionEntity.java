package net.piotrl.music.rescuetime.activity.repository;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rescuetime_action")
public class ActionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private Long categoryId;
}
