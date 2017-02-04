package net.piotrl.music.aggregation.repository;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "aggregation_userdata")
public class AggregationMetadataEntity {
    @Id
    @Column
    private Long accountId;

    @Column
    private String lastfmUsername;
    @Column
    private String lastfmApiKey;
    @Column
    private String lastfmSecureKey;

    @Column
    private String rescuetimeApiKey;
}
