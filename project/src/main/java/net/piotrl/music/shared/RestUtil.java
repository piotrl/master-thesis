package net.piotrl.music.shared;

import com.google.gson.Gson;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class RestUtil {

    public Map<String, String> buildParams(Object queryParameters) {
        Gson gson = new Gson();
        String json = gson.toJson(queryParameters);

        Map<String, String> params = new HashMap<>();
        params = gson.fromJson(json, params.getClass());

        return params;
    }
}
