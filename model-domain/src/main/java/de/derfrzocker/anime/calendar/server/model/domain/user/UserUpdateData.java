package de.derfrzocker.anime.calendar.server.model.domain.user;

import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.core.util.Change;
import java.util.Set;

public record UserUpdateData(Change<Set<CalendarId>> calendars) {

    public static UserUpdateData addCalendar(CalendarId calendarId) {
        return new UserUpdateData(Change.addingToSet(calendarId));
    }
}
