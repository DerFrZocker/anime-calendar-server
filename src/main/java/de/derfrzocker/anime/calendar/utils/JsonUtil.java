package de.derfrzocker.anime.calendar.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public final class JsonUtil {

    private JsonUtil() {
    }

    public static int getIntValue(JsonObject jsonObject, String key) {
        JsonElement element = jsonObject.get(key);

        if (!element.isJsonPrimitive()) {
            throw new IllegalArgumentException("Expected JsonElement of type JsonPrimitive for key " + key + " but got " + jsonObject.getClass());
        }

        if (!element.getAsJsonPrimitive().isNumber()) {
            throw new IllegalArgumentException("Expected JsonPrimitive with a number value but got " + element.getAsJsonPrimitive().getAsString());
        }

        return element.getAsNumber().intValue();
    }

    public static String getStringValue(JsonObject jsonObject, String key) {
        JsonElement element = jsonObject.get(key);

        if (!element.isJsonPrimitive()) {
            throw new IllegalArgumentException("Expected JsonElement of type JsonPrimitive for key " + key + " but got " + jsonObject.getClass());
        }

        if (!element.getAsJsonPrimitive().isString()) {
            throw new IllegalArgumentException("Expected JsonPrimitive with a string value but got " + element.getAsJsonPrimitive().getAsString());
        }

        return element.getAsString();
    }
}
