package de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi;

import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.permission.BiPredicateParser;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import java.util.HashMap;
import java.util.Map;

public class SameActionAndCalendarIdBiPredicateParser implements BiPredicateParser<SameActionAndCalendarIdBiPredicate> {

    private static final String ACTION = "action";
    private static final String CALENDAR_ID = "id";

    @Override
    public SameActionAndCalendarIdBiPredicate decode(Map<String, Object> values) {
        String actionValue = (String) values.get(ACTION);
        String idValue = (String) values.get(CALENDAR_ID);

        PermissionAction action = null;
        CalendarId id = null;

        if (actionValue != null) {
            action = new PermissionAction(actionValue);
        }

        if (idValue != null) {
            id = new CalendarId(idValue);
        }

        return new SameActionAndCalendarIdBiPredicate(action, id);
    }

    @Override
    public Map<String, Object> encode(SameActionAndCalendarIdBiPredicate predicate) {
        Map<String, Object> values = new HashMap<>();

        values.put(ACTION, predicate.action() == null ? null : predicate.action().raw());
        values.put(CALENDAR_ID, predicate.id() == null ? null : predicate.id().raw());

        return values;
    }
}
