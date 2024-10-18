package de.derfrzocker.anime.calendar.integration.mongodb.codec;

import de.derfrzocker.anime.calendar.server.model.core.IntegrationAnimeId;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class IntegrationAnimeIdCodec implements Codec<IntegrationAnimeId> {

    @Override
    public IntegrationAnimeId decode(BsonReader reader, DecoderContext decoderContext) {
        return new IntegrationAnimeId(reader.readString());
    }

    @Override
    public void encode(BsonWriter writer, IntegrationAnimeId value, EncoderContext encoderContext) {
        writer.writeString(value.raw());
    }

    @Override
    public Class<IntegrationAnimeId> getEncoderClass() {
        return IntegrationAnimeId.class;
    }
}
