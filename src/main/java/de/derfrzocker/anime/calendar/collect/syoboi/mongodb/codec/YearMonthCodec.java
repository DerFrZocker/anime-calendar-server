package de.derfrzocker.anime.calendar.collect.syoboi.mongodb.codec;

import java.time.YearMonth;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class YearMonthCodec implements Codec<YearMonth> {

    @Override
    public YearMonth decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        int year = reader.readInt32("Year");
        int month = reader.readInt32("Month");
        reader.readEndDocument();

        return YearMonth.of(year, month);
    }

    @Override
    public void encode(BsonWriter writer, YearMonth value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeInt32("Year", value.getYear());
        writer.writeInt32("Month", value.getMonthValue());
        writer.writeEndDocument();
    }

    @Override
    public Class<YearMonth> getEncoderClass() {
        return YearMonth.class;
    }
}
