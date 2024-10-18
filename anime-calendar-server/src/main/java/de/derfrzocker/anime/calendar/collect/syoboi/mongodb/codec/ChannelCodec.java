package de.derfrzocker.anime.calendar.collect.syoboi.mongodb.codec;

import de.derfrzocker.anime.calendar.collect.syoboi.Channel;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class ChannelCodec implements Codec<Channel> {

    @Override
    public Channel decode(BsonReader reader, DecoderContext decoderContext) {
        return new Channel(reader.readString());
    }

    @Override
    public void encode(BsonWriter writer, Channel value, EncoderContext encoderContext) {
        writer.writeString(value.channel());
    }

    @Override
    public Class<Channel> getEncoderClass() {
        return Channel.class;
    }
}
