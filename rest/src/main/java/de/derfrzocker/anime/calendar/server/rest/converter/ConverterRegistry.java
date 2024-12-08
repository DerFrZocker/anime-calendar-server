package de.derfrzocker.anime.calendar.server.rest.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserToken;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;
import java.io.IOException;

@Singleton
public class ConverterRegistry implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper objectMapper) {
        SimpleModule module = new SimpleModule();

        module.addSerializer(UserId.class, createJsonSerializer(((value, gen) -> gen.writeString(value.raw()))))
              .addDeserializer(UserId.class, createJsonDeserializer(p -> new UserId(p.getValueAsString())))
              .addSerializer(UserToken.class, createJsonSerializer(((value, gen) -> gen.writeString(value.raw()))))
              .addDeserializer(UserToken.class, createJsonDeserializer(p -> new UserToken(p.getValueAsString())))
              .addSerializer(CalendarId.class, createJsonSerializer(((value, gen) -> gen.writeString(value.raw()))))
              .addDeserializer(CalendarId.class, createJsonDeserializer(p -> new CalendarId(p.getValueAsString())))
              .addSerializer(CalendarKey.class, createJsonSerializer(((value, gen) -> gen.writeString(value.raw()))))
              .addDeserializer(CalendarKey.class, createJsonDeserializer(p -> new CalendarKey(p.getValueAsString())))
              .addSerializer(AnimeId.class, createJsonSerializer(((value, gen) -> gen.writeString(value.raw()))))
              .addDeserializer(AnimeId.class, createJsonDeserializer(p -> new AnimeId(p.getValueAsString())));

        objectMapper.registerModule(module);
    }

    private <T> JsonSerializer<T> createJsonSerializer(ConsumerJsonSerializer<T> serializer) {
        return new InlineJsonSerializer<>(serializer);
    }

    private <T> JsonDeserializer<T> createJsonDeserializer(ProducerJsonDeserializer<T> deserializer) {
        return new InlineJsonDeserializer<>(deserializer);
    }

    private static class InlineJsonSerializer<T> extends JsonSerializer<T> {

        private final ConsumerJsonSerializer<T> consumer;

        private InlineJsonSerializer(ConsumerJsonSerializer<T> consumer) {
            this.consumer = consumer;
        }

        @Override
        public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            consumer.consume(value, gen);
        }
    }

    private static class InlineJsonDeserializer<T> extends JsonDeserializer<T> {

        private final ProducerJsonDeserializer<T> producer;

        private InlineJsonDeserializer(ProducerJsonDeserializer<T> producer) {
            this.producer = producer;
        }

        @Override
        public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            return producer.produce(p);
        }
    }

    private interface ConsumerJsonSerializer<T> {

        void consume(T value, JsonGenerator gen) throws IOException;
    }

    private interface ProducerJsonDeserializer<T> {

        T produce(JsonParser p) throws IOException, JacksonException;
    }
}
