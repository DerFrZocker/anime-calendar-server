package de.derfrzocker.anime.calendar.server.impl.permission.predicate;

import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PredicateParser;
import java.util.HashMap;
import java.util.Map;

public class SameCalendarIdPredicateParser implements PredicateParser<SameCalendarIdPredicate> {

    private static final String CALENDAR_ID = "id";

    @Override
    public SameCalendarIdPredicate decode(Map<String, Object> values) {
        String value = (String) values.get(CALENDAR_ID);

        if (value == null) {
            return new SameCalendarIdPredicate(null);
        }

        return new SameCalendarIdPredicate(new CalendarId(value));
    }

    @Override
    public Map<String, Object> encode(SameCalendarIdPredicate predicate) {
        Map<String, Object> values = new HashMap<>();

        values.put(CALENDAR_ID, predicate.id() == null ? null : predicate.id().raw());

        return values;
    }
}
