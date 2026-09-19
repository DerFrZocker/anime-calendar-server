package de.derfrzocker.anime.calendar.mongodb.codec;

import de.derfrzocker.anime.calendar.actor.ActorId;
import de.derfrzocker.anime.calendar.anime.AnimeId;
import de.derfrzocker.anime.calendar.anime.LayerConfigDataType;
import de.derfrzocker.anime.calendar.anime.LayerType;

import java.time.YearMonth;

public class CustomCodecProvider extends AbstractCodecProvider {

    {
        putUUID(ActorId.class, ActorId::raw, ActorId::of);
        putUUID(AnimeId.class, AnimeId::raw, AnimeId::of);
        putString(LayerConfigDataType.class, LayerConfigDataType::raw, LayerConfigDataType::of);
        putString(LayerType.class, LayerType::raw, LayerType::of);
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
