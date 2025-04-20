package de.derfrzocker.anime.calendar.server.layer.common.parser;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.layer.common.config.SimpleOffsetIntegerLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.parser.AbstractLayerConfigParser;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class SimpleOffsetIntegerLayerConfigParser extends AbstractLayerConfigParser<SimpleOffsetIntegerLayerConfig> {

    public static final LayerParserKey PARSER_KEY = new LayerParserKey("simple-offset-integer-layer");
    public static final SimpleOffsetIntegerLayerConfigParser INSTANCE = new SimpleOffsetIntegerLayerConfigParser();

    private static final String VALUE_KEY = "value";
    private static final String OFFSET_KEY = "offset";

    private SimpleOffsetIntegerLayerConfigParser() {
    }

    @Override
    public @NotNull SimpleOffsetIntegerLayerConfig decode(@NotNull Map<String, Object> values) {

        LayerKey key = decodeLayerKey(values);
        int value = decodeInt(values, VALUE_KEY);
        int offset = decodeInt(values, OFFSET_KEY, 0);

        return new SimpleOffsetIntegerLayerConfig(key, value, offset);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull SimpleOffsetIntegerLayerConfig config) {
        Map<String, Object> values = new LinkedHashMap<>();

        encodeLayerKey(values, config.key());
        encodeInt(values, VALUE_KEY, config.value());
        encodeInt(values, OFFSET_KEY, config.offset());

        return values;
    }
}
