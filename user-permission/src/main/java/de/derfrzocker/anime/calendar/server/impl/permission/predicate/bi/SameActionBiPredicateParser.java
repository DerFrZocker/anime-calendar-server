package de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi;

import de.derfrzocker.anime.calendar.server.model.domain.permission.BiPredicateParser;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import java.util.HashMap;
import java.util.Map;

public class SameActionBiPredicateParser<T> implements BiPredicateParser<SameActionBiPredicate<T>> {

    private static final String ACTION = "action";

    @Override
    public SameActionBiPredicate<T> decode(Map<String, Object> values) {
        String value = (String) values.get(ACTION);

        if (value == null) {
            return new SameActionBiPredicate<>(null);
        }

        return new SameActionBiPredicate<>(new PermissionAction(value));
    }

    @Override
    public Map<String, Object> encode(SameActionBiPredicate<T> predicate) {
        Map<String, Object> values = new HashMap<>();

        values.put(ACTION, predicate.action() == null ? null : predicate.action().raw());

        return values;
    }
}
