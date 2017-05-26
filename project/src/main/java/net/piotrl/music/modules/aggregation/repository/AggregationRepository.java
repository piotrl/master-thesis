package net.piotrl.music.modules.aggregation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class AggregationRepository {
    private final JdbcOperations operations;

    @Autowired
    public AggregationRepository(JdbcOperations operations) {
        this.operations = operations;
    }

    public void refreshViews() {
        String sql = "REFRESH MATERIALIZED VIEW music_activity";

        operations.update(sql);
    }
}
