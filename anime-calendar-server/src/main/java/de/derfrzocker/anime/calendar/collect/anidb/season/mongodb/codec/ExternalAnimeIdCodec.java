package de.derfrzocker.anime.calendar.collect.anidb.season.mongodb.codec;

import de.derfrzocker.anime.calendar.collect.anidb.ExternalAnimeId;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class ExternalAnimeIdCodec implements Codec<ExternalAnimeId> {

    @Override
    public ExternalAnimeId decode(BsonReader reader, DecoderContext decoderContext) {
        return new ExternalAnimeId(reader.readString());
    }

    @Override
    public void encode(BsonWriter writer, ExternalAnimeId value, EncoderContext encoderContext) {
        writer.writeString(value.raw());
    }

    @Override
    public Class<ExternalAnimeId> getEncoderClass() {
        return ExternalAnimeId.class;
    }
}
