package de.derfrzocker.anime.calendar.mongodb.codec;

import de.derfrzocker.anime.calendar.actor.ActorId;

import java.time.YearMonth;

public class CustomCodecProvider extends AbstractCodecProvider {

    {
        putUUID(ActorId.class, ActorId::raw, ActorId::of);
        put(
                YearMonth.class, (value, writer) -> {
                    writer.writeStartDocument();
                    writer.writeInt32("year", value.getYear());
                    writer.writeInt32("month", value.getMonthValue());
                    writer.writeEndDocument();
                }, reader -> {
                    reader.readStartDocument();
                    int year = reader.readInt32("year");
                    int month = reader.readInt32("month");
                    reader.readEndDocument();

                    return YearMonth.of(year, month);
                });
    }
}
