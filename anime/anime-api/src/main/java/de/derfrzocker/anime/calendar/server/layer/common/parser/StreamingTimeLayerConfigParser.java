package de.derfrzocker.anime.calendar.server.layer.common.parser;

import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import de.derfrzocker.anime.calendar.server.episode.api.StreamType;
import de.derfrzocker.anime.calendar.server.layer.common.config.StreamingTimeLayerConfig;
import de.derfrzocker.anime.calendar.server.layer.parser.AbstractLayerConfigParser;
import java.time.Instant;
import java.time.Period;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class StreamingTimeLayerConfigParser extends AbstractLayerConfigParser<StreamingTimeLayerConfig> {

    public static final LayerParserKey PARSER_KEY = new LayerParserKey("streaming-time-layer");
    public static final StreamingTimeLayerConfigParser INSTANCE = new StreamingTimeLayerConfigParser();

    private static final String START_TIME_KEY = "start-time";
    private static final String PERIOD_KEY = "period";
    private static final String OFFSET_KEY = "offset";
    private static final String TYPE_KEY = "type";

    private StreamingTimeLayerConfigParser() {
    }

    @Override
    public @NotNull StreamingTimeLayerConfig decode(@NotNull Map<String, Object> values) {

        LayerKey key = decodeLayerKey(values);
        Instant startTime = decodeInstant(values, START_TIME_KEY);
        Period period = decodePeriod(values, PERIOD_KEY);
        int offset = decodeInt(values, OFFSET_KEY, 0);
        StreamType type = new StreamType(decodeString(values, TYPE_KEY, "org"));

        return new StreamingTimeLayerConfig(key, startTime, period, offset, type);
    }

    @Override
    public @NotNull Map<String, Object> encode(@NotNull StreamingTimeLayerConfig config) {
        Map<String, Object> values = new LinkedHashMap<>();

        encodeLayerKey(values, config.key());
        encodeInstant(values, START_TIME_KEY, config.startTime());
        encodePeriod(values, PERIOD_KEY, config.period());
        encodeInt(values, OFFSET_KEY, config.offset());
        encodeString(values, TYPE_KEY, config.type().raw());

        return values;
    }
}
