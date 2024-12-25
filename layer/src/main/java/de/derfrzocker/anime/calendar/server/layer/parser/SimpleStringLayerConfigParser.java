package de.derfrzocker.anime.calendar.server.layer.parser;

import de.derfrzocker.anime.calendar.server.layer.config.SimpleStringLayerConfig;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class SimpleStringLayerConfigParser extends AbstractLayerConfigParser<SimpleStringLayerConfig> {

    public static final SimpleStringLayerConfigParser INSTANCE = new SimpleStringLayerConfigParser();

    @Override
    public @NotNull SimpleStringLayerConfig decode(@NotNull Map<String, Object> values) {

        String value = decodeString(values, "value");

        return new SimpleStringLayerConfig(value);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull SimpleStringLayerConfig transformerConfig) {
        Map<String, Object> result = new HashMap<>();

        result.put("value", transformerConfig.value());

        return result;
    }
}
