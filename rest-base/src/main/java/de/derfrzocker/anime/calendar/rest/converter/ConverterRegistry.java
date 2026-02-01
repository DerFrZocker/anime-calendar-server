package de.derfrzocker.anime.calendar.rest.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserToken;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.function.Function;

@Singleton
public class ConverterRegistry
        implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper objectMapper) {
        SimpleModule module = new SimpleModule();

        register(module, AnimeId.class, AnimeId::raw, AnimeId::of);
        register(module, CalendarId.class, CalendarId::raw, CalendarId::of);
        register(module, CalendarKey.class, CalendarKey::raw, CalendarKey::of);
        register(module, IntegrationAnimeId.class, IntegrationAnimeId::raw, IntegrationAnimeId::new);
        register(module, IntegrationId.class, IntegrationId::raw, IntegrationId::of);
        register(module, UserId.class, UserId::raw, UserId::of);
        register(module, UserToken.class, UserToken::raw, UserToken::new);

        objectMapper.registerModule(module);
    }

    private <T> void register(
            SimpleModule module,
            Class<T> clazz,
            Function<T, String> serializer,
            Function<String, T> deserializer) {
        module
                .addSerializer(clazz, createJsonSerializer((value, gen) -> gen.writeString(serializer.apply(value))))
                .addDeserializer(clazz, createJsonDeserializer(p -> deserializer.apply(p.getValueAsString())));
    }

    private <T> JsonSerializer<T> createJsonSerializer(ConsumerJsonSerializer<T> serializer) {
        return new InlineJsonSerializer<>(serializer);
    }

    private <T> JsonDeserializer<T> createJsonDeserializer(ProducerJsonDeserializer<T> deserializer) {
        return new InlineJsonDeserializer<>(deserializer);
    }

    private static class InlineJsonSerializer<T>
            extends JsonSerializer<T> {

        private final ConsumerJsonSerializer<T> consumer;

        private InlineJsonSerializer(ConsumerJsonSerializer<T> consumer) {
            this.consumer = consumer;
        }

        @Override
        public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            consumer.consume(value, gen);
        }
    }

    private static class InlineJsonDeserializer<T>
            extends JsonDeserializer<T> {

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
