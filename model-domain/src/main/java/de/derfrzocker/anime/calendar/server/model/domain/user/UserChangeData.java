package de.derfrzocker.anime.calendar.server.model.domain.user;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.util.Change;
import de.derfrzocker.anime.calendar.server.model.domain.util.SetAddChange;
import java.util.Set;

public record UserChangeData(Change<Set<CalendarId>> calendars) {

    public static UserChangeData addCalendar(CalendarId calendarId) {
        return new UserChangeData(new SetAddChange<>(calendarId));
    }
}
