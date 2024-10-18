package de.derfrzocker.anime.calendar.collect.syoboi.mongodb.codec;

import de.derfrzocker.anime.calendar.collect.syoboi.TID;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class TIDCodec implements Codec<TID> {

    @Override
    public TID decode(BsonReader reader, DecoderContext decoderContext) {
        return new TID(reader.readString());
    }

    @Override
    public void encode(BsonWriter writer, TID value, EncoderContext encoderContext) {
        writer.writeString(value.tid());
    }

    @Override
    public Class<TID> getEncoderClass() {
        return TID.class;
    }
}
