package de.derfrzocker.anime.calendar.server.layer2.common.parser;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.layer2.common.config.SimpleStringLayerConfig;
import de.derfrzocker.anime.calendar.server.layer2.parser.AbstractLayerConfigParser;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class SimpleStringLayerConfigParser extends AbstractLayerConfigParser<SimpleStringLayerConfig> {

    public static final LayerParserKey PARSER_KEY = new LayerParserKey("simple-string-layer");
    public static final SimpleStringLayerConfigParser INSTANCE = new SimpleStringLayerConfigParser();

    private static final String VALUE_KEY = "value";

    private SimpleStringLayerConfigParser() {
    }

    @Override
    public @NotNull SimpleStringLayerConfig decode(@NotNull Map<String, Object> values) {

        LayerKey key = decodeLayerKey(values);
        String value = decodeString(values, VALUE_KEY);

        return new SimpleStringLayerConfig(key, value);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull SimpleStringLayerConfig config) {
        Map<String, Object> values = new LinkedHashMap<>();

        encodeLayerKey(values, config.key());
        encodeString(values, VALUE_KEY, config.value());

        return values;
    }
}
