package de.derfrzocker.anime.calendar.plugin.mongodb.codec;

import de.derfrzocker.anime.calendar.server.model.core.AnimeId;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class AnimeIdCodec implements Codec<AnimeId> {

    @Override
    public AnimeId decode(BsonReader reader, DecoderContext decoderContext) {
        return new AnimeId(reader.readString());
    }

    @Override
    public void encode(BsonWriter writer, AnimeId value, EncoderContext encoderContext) {
        writer.writeString(value.raw());
    }

    @Override
    public Class<AnimeId> getEncoderClass() {
        return AnimeId.class;
    }
}
