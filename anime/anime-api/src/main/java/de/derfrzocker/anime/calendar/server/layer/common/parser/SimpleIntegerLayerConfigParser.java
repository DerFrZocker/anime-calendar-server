package de.derfrzocker.anime.calendar.server.layer.common.parser;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.layer.common.config.SimpleIntegerLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.parser.AbstractLayerConfigParser;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class SimpleIntegerLayerConfigParser extends AbstractLayerConfigParser<SimpleIntegerLayerConfig> {

    public static final LayerParserKey PARSER_KEY = new LayerParserKey("simple-integer-layer");
    public static final SimpleIntegerLayerConfigParser INSTANCE = new SimpleIntegerLayerConfigParser();

    private static final String VALUE_KEY = "value";

    private SimpleIntegerLayerConfigParser() {
    }

    @Override
    public @NotNull SimpleIntegerLayerConfig decode(@NotNull Map<String, Object> values) {

        LayerKey key = decodeLayerKey(values);
        int value = decodeInt(values, VALUE_KEY);

        return new SimpleIntegerLayerConfig(key, value);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull SimpleIntegerLayerConfig config) {
        Map<String, Object> values = new LinkedHashMap<>();

        encodeLayerKey(values, config.key());
        encodeInt(values, VALUE_KEY, config.value());

        return values;
    }
}
