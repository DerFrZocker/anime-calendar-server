package de.derfrzocker.anime.calendar.integration.mongodb.codec;

import de.derfrzocker.anime.calendar.server.model.core.IntegrationId;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class IntegrationIdCodec implements Codec<IntegrationId> {

    @Override
    public IntegrationId decode(BsonReader reader, DecoderContext decoderContext) {
        return new IntegrationId(reader.readString());
    }

    @Override
    public void encode(BsonWriter writer, IntegrationId value, EncoderContext encoderContext) {
        writer.writeString(value.raw());
    }

    @Override
    public Class<IntegrationId> getEncoderClass() {
        return IntegrationId.class;
    }
}
