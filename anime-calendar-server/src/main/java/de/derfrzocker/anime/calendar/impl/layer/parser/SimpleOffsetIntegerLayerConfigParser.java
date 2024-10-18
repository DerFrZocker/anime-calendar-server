package de.derfrzocker.anime.calendar.impl.layer.parser;

import de.derfrzocker.anime.calendar.impl.layer.config.SimpleOffsetIntegerLayerConfig;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class SimpleOffsetIntegerLayerConfigParser extends AbstractLayerConfigParser<SimpleOffsetIntegerLayerConfig> {

    public static final SimpleOffsetIntegerLayerConfigParser INSTANCE = new SimpleOffsetIntegerLayerConfigParser();

    private SimpleOffsetIntegerLayerConfigParser() {
    }

    @Override
    public @NotNull SimpleOffsetIntegerLayerConfig decode(@NotNull Map<String, Object> values) {

        int value = decodeInt(values, "value");
        int offset = decodeInt(values, "offset", 0);

        return new SimpleOffsetIntegerLayerConfig(value, offset);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull SimpleOffsetIntegerLayerConfig transformerConfig) {
        Map<String, Object> result = new LinkedHashMap<>();

        result.put("value", transformerConfig.value());
        result.put("offset", transformerConfig.offset());

        return result;
    }
}
