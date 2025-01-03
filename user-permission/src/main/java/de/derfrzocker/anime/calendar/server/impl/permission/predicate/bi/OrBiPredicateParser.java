package de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi;

import de.derfrzocker.anime.calendar.server.model.domain.permission.BiPredicateParser;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class OrBiPredicateParser<V> implements BiPredicateParser<OrBiPredicate<V>> {

    private static final String PREDICATES = "predicates";

    private final Function<Map<String, Object>, BiPredicate<PermissionAction, V>> subDecoder;
    private final Function<BiPredicate<PermissionAction, V>, Map<String, Object>> subEncoder;

    public OrBiPredicateParser(Function<Map<String, Object>, BiPredicate<PermissionAction, V>> subDecoder,
                               Function<BiPredicate<PermissionAction, V>, Map<String, Object>> subEncoder) {
        this.subDecoder = subDecoder;
        this.subEncoder = subEncoder;
    }

    @Override
    public OrBiPredicate<V> decode(Map<String, Object> values) {
        List<Map<String, Object>> value = (List<Map<String, Object>>) values.get(PREDICATES);

        if (value == null) {
            return new OrBiPredicate<>(List.of());
        }

        List<BiPredicate<PermissionAction, V>> predicates = new ArrayList<>(value.size());
        for (Map<String, Object> subPredicate : value) {
            predicates.add(this.subDecoder.apply(subPredicate));
        }

        return new OrBiPredicate<>(Collections.unmodifiableList(predicates));
    }

    @Override
    public Map<String, Object> encode(OrBiPredicate<V> predicate) {
        Map<String, Object> values = new HashMap<>();
        List<Map<String, Object>> predicates = new ArrayList<>();

        for (BiPredicate<PermissionAction, V> other : predicate.predicates()) {
            predicates.add(this.subEncoder.apply(other));
        }

        values.put(PREDICATES, predicates);
        return values;
    }
}
