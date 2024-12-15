package de.derfrzocker.anime.calendar.server.impl.permission.predicate;

import de.derfrzocker.anime.calendar.server.model.domain.permission.PredicateParser;
import java.util.HashMap;
import java.util.Map;

public class FixedPredicateParser implements PredicateParser<FixedPredicate<?>> {

    private static final String VALUE = "value";

    @Override
    public FixedPredicate<?> decode(Map<String, Object> values) {
        boolean value = (boolean) values.get(VALUE);

        return new FixedPredicate<>(value);
    }

    @Override
    public Map<String, Object> encode(FixedPredicate<?> predicate) {
        Map<String, Object> values = new HashMap<>();

        values.put(VALUE, predicate.value());

        return values;
    }
}
