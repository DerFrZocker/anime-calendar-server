package de.derfrzocker.anime.calendar.server.layer.impl.service;

import static de.derfrzocker.anime.calendar.server.layer.exception.LayerConfigParserExceptions.inconsistentNotFound;
import de.derfrzocker.anime.calendar.core.exception.InconsistentDataException;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.layer.api.LayerConfig;
import de.derfrzocker.anime.calendar.server.layer.common.parser.BoundFilterConfigParser;
import de.derfrzocker.anime.calendar.server.layer.common.parser.IntegrationUrlLayerConfigParser;
import de.derfrzocker.anime.calendar.server.layer.common.parser.LocalizedNameLayerConfigParser;
import de.derfrzocker.anime.calendar.server.layer.common.parser.RegionFilterConfigParser;
import de.derfrzocker.anime.calendar.server.layer.common.parser.SimpleIntegerLayerConfigParser;
import de.derfrzocker.anime.calendar.server.layer.common.parser.SimpleOffsetIntegerLayerConfigParser;
import de.derfrzocker.anime.calendar.server.layer.common.parser.SimpleStringLayerConfigParser;
import de.derfrzocker.anime.calendar.server.layer.common.parser.StreamingTimeLayerConfigParser;
import de.derfrzocker.anime.calendar.server.layer.common.parser.StreamingUrlLayerConfigParser;
import de.derfrzocker.anime.calendar.server.layer.parser.LayerConfigParser;
import de.derfrzocker.anime.calendar.server.layer.service.LayerConfigParserService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class LayerConfigParserServiceImpl implements LayerConfigParserService {

    private static final String LAYER_PARSER_KEY_KEY = "layer-parser-key";
    private final Map<LayerParserKey, LayerConfigParser<?>> layerConfigParsers = new HashMap<>();

    @Override
    public <T extends LayerConfig> T decode(Map<String, Object> values) {
        Object parserKey = values.get(LAYER_PARSER_KEY_KEY);

        if (parserKey == null) {
            throw InconsistentDataException.from(
                    "No '%s' was found in '%s', which is required to select the right parse".formatted(
                            LAYER_PARSER_KEY_KEY,
                            values)).get();
        }

        LayerConfigParser<T> parser = getParser(new LayerParserKey(String.valueOf(parserKey)));

        return parser.decode(values);
    }

    @Override
    public <T extends LayerConfig> Map<String, Object> encode(T config) {
        LayerConfigParser<T> parser = getParser(config.parserKey());

        Map<String, Object> values = parser.encode(config);

        values.put(LAYER_PARSER_KEY_KEY, config.parserKey().raw());

        return values;
    }

    private <T extends LayerConfig> LayerConfigParser<T> getParser(LayerParserKey key) {
        LayerConfigParser<?> parser = this.layerConfigParsers.get(key);

        if (parser == null) {
            throw inconsistentNotFound(key).get();
        }

        return (LayerConfigParser<T>) parser;
    }

    private void register(LayerParserKey key, LayerConfigParser<?> layerConfigParser) {
        this.layerConfigParsers.put(key, layerConfigParser);
    }

    @PostConstruct
    void registerLayers() {
        register(BoundFilterConfigParser.PARSER_KEY, BoundFilterConfigParser.INSTANCE);
        register(IntegrationUrlLayerConfigParser.PARSER_KEY, IntegrationUrlLayerConfigParser.INSTANCE);
        register(LocalizedNameLayerConfigParser.PARSER_KEY, LocalizedNameLayerConfigParser.INSTANCE);
        register(RegionFilterConfigParser.PARSER_KEY, RegionFilterConfigParser.INSTANCE);
        register(SimpleIntegerLayerConfigParser.PARSER_KEY, SimpleIntegerLayerConfigParser.INSTANCE);
        register(SimpleOffsetIntegerLayerConfigParser.PARSER_KEY, SimpleOffsetIntegerLayerConfigParser.INSTANCE);
        register(SimpleStringLayerConfigParser.PARSER_KEY, SimpleStringLayerConfigParser.INSTANCE);
        register(StreamingTimeLayerConfigParser.PARSER_KEY, StreamingTimeLayerConfigParser.INSTANCE);
        register(StreamingUrlLayerConfigParser.PARSER_KEY, StreamingUrlLayerConfigParser.INSTANCE);
    }
}
