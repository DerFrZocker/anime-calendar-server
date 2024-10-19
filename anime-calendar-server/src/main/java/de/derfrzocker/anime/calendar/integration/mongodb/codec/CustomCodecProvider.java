package de.derfrzocker.anime.calendar.integration.mongodb.codec;

import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.server.model.core.integration.IntegrationId;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class CustomCodecProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == IntegrationId.class) {
            return (Codec<T>) new IntegrationIdCodec();
        }
        if (clazz == IntegrationAnimeId.class) {
            return (Codec<T>) new IntegrationAnimeIdCodec();
        }

        return null;
    }
}
