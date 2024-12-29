package de.derfrzocker.anime.calendar.server.impl.permission.predicate;

import de.derfrzocker.anime.calendar.server.model.domain.permission.PredicateParser;
import java.util.HashMap;
import java.util.Map;

public class FixedPredicateParser<V> implements PredicateParser<FixedPredicate<V>> {

    private static final String VALUE = "value";

    @Override
    public FixedPredicate<V> decode(Map<String, Object> values) {
        boolean value = (boolean) values.get(VALUE);

        return new FixedPredicate<>(value);
    }

    @Override
    public Map<String, Object> encode(FixedPredicate<V> predicate) {
        Map<String, Object> values = new HashMap<>();

        values.put(VALUE, predicate.value());

        return values;
    }
}
