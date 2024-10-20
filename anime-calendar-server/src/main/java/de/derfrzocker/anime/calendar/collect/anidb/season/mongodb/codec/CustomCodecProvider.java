package de.derfrzocker.anime.calendar.collect.anidb.season.mongodb.codec;

import de.derfrzocker.anime.calendar.collect.anidb.ExternalAnimeId;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class CustomCodecProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == ExternalAnimeId.class) {
            return (Codec<T>) new ExternalAnimeIdCodec();
        }

        return null;
    }
}
