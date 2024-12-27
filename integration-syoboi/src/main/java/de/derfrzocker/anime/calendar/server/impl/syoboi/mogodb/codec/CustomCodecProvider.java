package de.derfrzocker.anime.calendar.server.impl.syoboi.mogodb.codec;

import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.Channel;
import de.derfrzocker.anime.calendar.server.impl.syoboi.domain.TID;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class CustomCodecProvider implements CodecProvider {

    private final Map<Class<?>, Codec<?>> codecs = new HashMap<>();

    {
        putString(TID.class, TID::raw, TID::new);
        putString(Channel.class, Channel::raw, Channel::new);
        put(YearMonth.class, (value, writer) -> {
            writer.writeStartDocument();
            writer.writeInt32("Year", value.getYear());
            writer.writeInt32("Month", value.getMonthValue());
            writer.writeEndDocument();
        }, reader -> {
            reader.readStartDocument();
            int year = reader.readInt32("Year");
            int month = reader.readInt32("Month");
            reader.readEndDocument();

            return YearMonth.of(year, month);
        });
    }

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        return (Codec<T>) codecs.get(clazz);
    }

    private <T> void putString(Class<T> clazz, Function<T, String> consumer, Function<String, T> producer) {
        put(clazz, (v, w) -> w.writeString(consumer.apply(v)), r -> producer.apply(r.readString()));
    }

    private <T> void put(Class<T> clazz, BiConsumer<T, BsonWriter> consumer, Function<BsonReader, T> producer) {
        this.codecs.put(clazz, new InlineCodec<>(clazz, consumer, producer));
    }

    private record InlineCodec<T>(Class<T> clazz, BiConsumer<T, BsonWriter> consumer,
                                  Function<BsonReader, T> producer) implements Codec<T> {

        @Override
        public T decode(BsonReader reader, DecoderContext decoderContext) {
            return this.producer.apply(reader);
        }

        @Override
        public void encode(BsonWriter writer, T value, EncoderContext encoderContext) {
            this.consumer.accept(value, writer);
        }

        @Override
        public Class<T> getEncoderClass() {
            return this.clazz;
        }
    }
}
