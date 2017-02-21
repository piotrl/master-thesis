package net.piotrl.music.modules.aggregation;

import com.google.common.collect.Lists;
import org.springframework.data.util.Pair;

import java.util.List;

public class AggregationResult {
    private boolean failed = false;
    private List<Pair<String, String>> errors = Lists.newArrayList();

    public void addError(String errorName, String details) {
        failed = true;
        errors.add(Pair.of(errorName, details));
    }
}
