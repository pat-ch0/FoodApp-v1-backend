package org.t1.foodApp.Utils;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class JsonUtil {
    private static final List<String> preferredLanguages = Arrays.asList("fr", "en", "es", "uk");

    public static String getLocalizedValue(JSONObject productData, String baseKey) {
        for (String lang : preferredLanguages) {
            String localizedKey = baseKey.replace("{lang}", lang);
            String value = getNestedJsonValue(productData, localizedKey);
            if (value != null && !value.isEmpty()) {
                return value;
            }
        }
        return "";
    }

    private static String getNestedJsonValue(JSONObject jsonObject, String path) {
        String[] keys = path.split("\\.");
        JSONObject currentObject = jsonObject;
        for (int i = 0; i < keys.length - 1; i++) {
            currentObject = currentObject.optJSONObject(keys[i]);
            if (currentObject == null) {
                return null;
            }
        }
        return currentObject.optString(keys[keys.length - 1]);
    }

}
