package de.derfrzocker.anime.calendar.api.transformer;

import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

public interface ConfigParser<T extends TransformerConfig> {

    @NotNull
    T fromJson(@NotNull JsonElement jsonElement);

    @NotNull
    JsonElement toJson(@NotNull T transformerConfig);
}
