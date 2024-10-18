package de.derfrzocker.anime.calendar.plugin.mongodb.codec;

import de.derfrzocker.anime.calendar.api.anime.AnimeId;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class CustomCodecProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == AnimeId.class) {
            return (Codec<T>) new AnimeIdCodec();
        }

        return null;
    }
}
