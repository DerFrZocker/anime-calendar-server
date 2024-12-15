package de.derfrzocker.anime.calendar.server.model.domain.permission;

import java.util.Map;
import java.util.function.Predicate;

public interface PredicateParser<T extends Predicate<?>> {

    T decode(Map<String, Object> values);

    Map<String, Object> encode(T predicate);
}
