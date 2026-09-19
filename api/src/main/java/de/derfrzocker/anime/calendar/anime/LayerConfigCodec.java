package de.derfrzocker.anime.calendar.anime;

import jakarta.json.JsonObject;

public interface LayerConfigCodec<T extends LayerConfigData> {

    T deserialize(JsonObject data);

    JsonObject serialize(T data);
}
