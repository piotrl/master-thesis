package net.piotrl.music.modules.aggregation.repository;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
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
