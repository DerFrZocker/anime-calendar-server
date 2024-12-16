package de.derfrzocker.anime.calendar.server.mongodb.codec;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.animeaccountlink.AnimeAccountLinkId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.user.HashedUserToken;
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
        putString(UserId.class, UserId::raw, UserId::new);
        putString(HashedUserToken.class, HashedUserToken::raw, HashedUserToken::new);
        putString(CalendarId.class, CalendarId::raw, CalendarId::new);
        putString(CalendarKey.class, CalendarKey::raw, CalendarKey::new);
        putString(AnimeAccountLinkId.class, AnimeAccountLinkId::raw, AnimeAccountLinkId::new);
        putString(AnimeId.class, AnimeId::raw, AnimeId::new);
        putString(IntegrationId.class, IntegrationId::raw, IntegrationId::new);
        putString(IntegrationAnimeId.class, IntegrationAnimeId::raw, IntegrationAnimeId::new);
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
