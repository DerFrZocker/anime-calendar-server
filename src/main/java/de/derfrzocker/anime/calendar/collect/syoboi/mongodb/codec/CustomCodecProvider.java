package de.derfrzocker.anime.calendar.collect.syoboi.mongodb.codec;

import de.derfrzocker.anime.calendar.collect.syoboi.Channel;
import de.derfrzocker.anime.calendar.collect.syoboi.TID;
import java.time.YearMonth;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class CustomCodecProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == TID.class) {
            return (Codec<T>) new TIDCodec();
        }
        if (clazz == Channel.class) {
            return (Codec<T>) new ChannelCodec();
        }
        if (clazz == YearMonth.class) {
            return (Codec<T>) new YearMonthCodec();
        }
        return null;
    }
}
