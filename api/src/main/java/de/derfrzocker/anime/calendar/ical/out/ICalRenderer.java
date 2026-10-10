package de.derfrzocker.anime.calendar.ical.out;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

public interface ICalRenderer {

    ICalRenderer prodId(String prodId);

    ICalRenderer uid(String uid);

    ICalRenderer event(Consumer<ICalEventRenderer> event);

    String render();

    interface ICalEventRenderer {

        ICalEventRenderer summary(String summary);

        ICalEventRenderer description(String description);

        ICalEventRenderer start(Instant start);

        ICalEventRenderer duration(Duration duration);

        ICalRenderer uid(String uid);
    }
}
