package de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import java.util.Objects;
import java.util.function.BiPredicate;

public record SameActionAndCalendarIdBiPredicate(PermissionAction action,
                                                 CalendarId id) implements BiPredicate<PermissionAction, CalendarId> {

    @Override
    public boolean test(PermissionAction action, CalendarId toTest) {
        return Objects.equals(action(), action) && Objects.equals(id(), toTest);
    }
}
