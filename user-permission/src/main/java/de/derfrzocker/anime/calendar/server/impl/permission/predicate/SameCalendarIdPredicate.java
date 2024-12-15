package de.derfrzocker.anime.calendar.server.impl.permission.predicate;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import java.util.Objects;
import java.util.function.Predicate;

public record SameCalendarIdPredicate(CalendarId id) implements Predicate<CalendarId> {

    @Override
    public boolean test(CalendarId toTest) {
        return Objects.equals(id(), toTest);
    }
}
