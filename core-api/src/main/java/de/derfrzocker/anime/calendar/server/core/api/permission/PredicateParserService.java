package de.derfrzocker.anime.calendar.server.core.api.permission;

import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public interface PredicateParserService {

    <T> Predicate<T> decode(Map<String, Object> values);

    <T> Map<String, Object> encode(Predicate<T> predicate);

    <T> BiPredicate<PermissionAction, T> decodeBi(Map<String, Object> values);

    <T> Map<String, Object> encodeBi(BiPredicate<PermissionAction, T> predicate);
}
