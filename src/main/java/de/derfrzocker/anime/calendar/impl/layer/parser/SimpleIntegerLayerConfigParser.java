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

        int minInclusive = decodeInt(values, "min-inclusive");
        int maxInclusive = decodeInt(values, "max-inclusive");
        int value = decodeInt(values, "value");

        return new SimpleIntegerLayerConfig(minInclusive, maxInclusive, value);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull SimpleIntegerLayerConfig transformerConfig) {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("min-inclusive", transformerConfig.getMinInclusive());
        result.put("max-inclusive", transformerConfig.getMaxInclusive());
        result.put("value", transformerConfig.getValue());

        return result;
    }
}
