package de.derfrzocker.anime.calendar.server.model.domain.permission;

import java.util.Map;
import java.util.function.BiPredicate;

public interface BiPredicateParser<T extends BiPredicate<PermissionAction, ?>> {

    T decode(Map<String, Object> values);

    Map<String, Object> encode(T predicate);
}
