package de.derfrzocker.anime.calendar.impl.layer.parser;

import de.derfrzocker.anime.calendar.impl.layer.config.SimpleIntegerLayerConfig;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class SimpleIntegerLayerConfigParser extends AbstractLayerConfigParser<SimpleIntegerLayerConfig> {

    public static final SimpleIntegerLayerConfigParser INSTANCE = new SimpleIntegerLayerConfigParser();

    private SimpleIntegerLayerConfigParser() {
    }

    @Override
    public @NotNull SimpleIntegerLayerConfig decode(@NotNull Map<String, Object> values) {

        int value = decodeInt(values, "value");

        return new SimpleIntegerLayerConfig(value);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull SimpleIntegerLayerConfig transformerConfig) {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("value", transformerConfig.value());

        return result;
    }
}
