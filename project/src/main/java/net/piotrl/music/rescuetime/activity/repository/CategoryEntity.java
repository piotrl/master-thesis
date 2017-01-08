package net.piotrl.music.rescuetime.activity.repository;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rescuetime_category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    public CategoryEntity() {}

    public CategoryEntity(String name) {
        this.name = name;
    }
}
