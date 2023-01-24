package de.derfrzocker.anime.calendar.impl.transformer.transformer;

import com.google.gson.JsonElement;
import de.derfrzocker.anime.calendar.api.transformer.ConfigParser;
import de.derfrzocker.anime.calendar.api.transformer.EpisodeTransformer;
import de.derfrzocker.anime.calendar.api.transformer.TransformerConfig;
import de.derfrzocker.anime.calendar.api.transformer.TransformerData;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractEpisodeTransformer<T extends TransformerConfig> implements EpisodeTransformer<T> {

    @NotNull
    private final String transformKey;
    @NotNull
    private final ConfigParser<T> configParser;

    protected AbstractEpisodeTransformer(@NotNull String transformKey, @NotNull ConfigParser<T> configParser) {
        this.transformKey = transformKey;
        this.configParser = configParser;
    }

    @Override
    public @NotNull String getTransformKey() {
        return transformKey;
    }

    @Override
    public @NotNull ConfigParser<T> getConfigParser() {
        return configParser;
    }

    @Override
    public @NotNull TransformerData<T> createTransformerData(JsonElement jsonElement) {
        return new TransformerData<>(this, getConfigParser().fromJson(jsonElement));
    }
}
