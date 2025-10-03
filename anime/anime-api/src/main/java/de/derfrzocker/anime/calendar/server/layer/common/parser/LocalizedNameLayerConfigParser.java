package de.derfrzocker.anime.calendar.server.layer.common.parser;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.layer.common.config.LocalizedNameLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.parser.AbstractLayerConfigParser;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class LocalizedNameLayerConfigParser extends AbstractLayerConfigParser<LocalizedNameLayerConfig> {

    public static final LayerParserKey PARSER_KEY = new LayerParserKey("localized-name-layer");
    public static final LocalizedNameLayerConfigParser INSTANCE = new LocalizedNameLayerConfigParser();

    private static final String LANGUAGE_KEY = "language";
    private static final String NAME_KEY = "name";

    private LocalizedNameLayerConfigParser() {
    }

    @Override
    public @NotNull LocalizedNameLayerConfig decode(@NotNull Map<String, Object> values) {

        LayerKey key = decodeLayerKey(values);
        String language = decodeString(values, LANGUAGE_KEY);
        String name = decodeString(values, NAME_KEY);

        return new LocalizedNameLayerConfig(key, language, name);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull LocalizedNameLayerConfig config) {
        Map<String, Object> values = new LinkedHashMap<>();

        encodeLayerKey(values, config.key());
        encodeString(values, LANGUAGE_KEY, config.language());
        encodeString(values, NAME_KEY, config.name());

        return values;
    }
}
