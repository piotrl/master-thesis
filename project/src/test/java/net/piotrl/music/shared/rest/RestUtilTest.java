package net.piotrl.music.shared.rest;

import net.piotrl.music.rescuetime.api.RescueTimeQueryParameters;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RestUtilTest {

    @Test
    public void filledOneParameter() throws Exception {
        RescueTimeQueryParameters parameters = RescueTimeQueryParameters.builder()
                .perspective("test")
                .build();
        Map<String, String> stringStringMap = RestUtil.buildParams(parameters);

        assertThat(stringStringMap).isNotNull();
        assertThat(stringStringMap).isNotEmpty();
        assertThat(stringStringMap).containsOnlyKeys("perspective");
        assertThat(stringStringMap).containsValue("test");
    }

    @Test
    public void emptyParameters() throws Exception {
        RescueTimeQueryParameters parameters = RescueTimeQueryParameters.builder().build();
        Map<String, String> stringStringMap = RestUtil.buildParams(parameters);

        assertThat(stringStringMap).isNotNull();
        assertThat(stringStringMap).isEmpty();
    }
}