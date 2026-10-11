package de.derfrzocker.anime.calendar.anime.layer.codec;

import de.derfrzocker.anime.calendar.anime.LayerConfigCodec;
import de.derfrzocker.anime.calendar.anime.StreamType;
import de.derfrzocker.anime.calendar.anime.layer.config.StreamingTimeConfig;
import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import java.time.Instant;
import java.time.Period;

@Identifier(StreamingTimeConfig.DATA_TYPE_RAW)
@ApplicationScoped
class StreamingTimeCodec implements LayerConfigCodec<StreamingTimeConfig> {

    private final static String START_TIME_KEY = "start_time";
    private final static String PERIOD_KEY = "period";
    private final static String OFFSET_KEY = "offset";
    private final static String TYPE_KEY = "type";

    @Override
    public StreamingTimeConfig deserialize(JsonObject data) {

        Instant startTime = Instant.parse(data.getString(START_TIME_KEY));
        Period period = Period.parse(data.getString(PERIOD_KEY));
        int offset = data.getInt(OFFSET_KEY);
        StreamType type = StreamType.of(data.getString(TYPE_KEY));

        return new StreamingTimeConfig(startTime, period, offset, type);
    }

    @Override
    public JsonObject serialize(StreamingTimeConfig data) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add(START_TIME_KEY, data.startTime().toString());
        builder.add(PERIOD_KEY, data.period().toString());
        builder.add(OFFSET_KEY, data.offset());
        builder.add(TYPE_KEY, data.type().raw());

        return builder.build();
    }
}
