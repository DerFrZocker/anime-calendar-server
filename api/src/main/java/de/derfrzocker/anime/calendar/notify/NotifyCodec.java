package de.derfrzocker.anime.calendar.notify;

import jakarta.json.JsonObject;

public interface NotifyCodec<T extends NotifyData> {

    T deserialize(JsonObject data);

    JsonObject serialize(T data);
}
