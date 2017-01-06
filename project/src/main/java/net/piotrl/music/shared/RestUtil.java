package net.piotrl.music.shared;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public final class RestUtil {

    public static Map<String, String> buildParams(Object queryParameters) {
        Gson gson = new Gson();
        String json = gson.toJson(queryParameters);

        Map<String, String> params = new HashMap<>();
        params = gson.fromJson(json, params.getClass());

        return params;
    }
}
