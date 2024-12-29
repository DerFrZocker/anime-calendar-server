package de.derfrzocker.anime.calendar.server.impl.permission.predicate;

import de.derfrzocker.anime.calendar.server.model.domain.permission.PredicateParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class AndPredicateParser<V> implements PredicateParser<AndPredicate<V>> {

    private static final String PREDICATES = "predicates";

    private final Function<Map<String, Object>, Predicate<V>> subDecoder;
    private final Function<Predicate<V>, Map<String, Object>> subEncoder;

    public AndPredicateParser(Function<Map<String, Object>, Predicate<V>> subDecoder,
                              Function<Predicate<V>, Map<String, Object>> subEncoder) {
        this.subDecoder = subDecoder;
        this.subEncoder = subEncoder;
    }

    @Override
    public AndPredicate<V> decode(Map<String, Object> values) {
        List<Map<String, Object>> value = (List<Map<String, Object>>) values.get(PREDICATES);

        if (value == null) {
            return new AndPredicate<>(List.of());
        }

        List<Predicate<V>> predicates = new ArrayList<>(value.size());
        for (Map<String, Object> subPredicate : value) {
            predicates.add(this.subDecoder.apply(subPredicate));
        }

        return new AndPredicate<>(Collections.unmodifiableList(predicates));
    }

    @Override
    public Map<String, Object> encode(AndPredicate<V> predicate) {
        Map<String, Object> values = new HashMap<>();
        List<Map<String, Object>> predicates = new ArrayList<>();

        for (Predicate<V> other : predicate.predicates()) {
            predicates.add(this.subEncoder.apply(other));
        }

        values.put(PREDICATES, predicates);
        return values;
    }
}
