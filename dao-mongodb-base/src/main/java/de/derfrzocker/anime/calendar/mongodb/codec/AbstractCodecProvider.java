package de.derfrzocker.anime.calendar.mongodb.codec;

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

public abstract class AbstractCodecProvider implements CodecProvider {

    private final Map<Class<?>, Codec<?>> codecs = new HashMap<>();

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        return (Codec<T>) codecs.get(clazz);
    }

    protected <T> void putString(Class<T> clazz, Function<T, String> consumer, Function<String, T> producer) {
        put(clazz, (v, w) -> w.writeString(consumer.apply(v)), r -> producer.apply(r.readString()));
    }

    protected <T> void put(Class<T> clazz, BiConsumer<T, BsonWriter> consumer, Function<BsonReader, T> producer) {
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
