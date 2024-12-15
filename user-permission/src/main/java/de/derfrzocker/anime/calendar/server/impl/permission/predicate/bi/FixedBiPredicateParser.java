package de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi;

import de.derfrzocker.anime.calendar.server.model.domain.permission.BiPredicateParser;
import java.util.HashMap;
import java.util.Map;

public class FixedBiPredicateParser implements BiPredicateParser<FixedBiPredicate<?>> {

    private static final String VALUE = "value";

    @Override
    public FixedBiPredicate<?> decode(Map<String, Object> values) {
        boolean value = (boolean) values.get(VALUE);

        return new FixedBiPredicate<>(value);
    }

    @Override
    public Map<String, Object> encode(FixedBiPredicate<?> predicate) {
        Map<String, Object> values = new HashMap<>();

        values.put(VALUE, predicate.value());

        return values;
    }
}
