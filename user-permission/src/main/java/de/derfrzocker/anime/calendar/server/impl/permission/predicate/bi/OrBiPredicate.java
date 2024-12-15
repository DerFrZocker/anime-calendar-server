package de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi;

import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import java.util.List;
import java.util.function.BiPredicate;

public record OrBiPredicate<T>(
        List<BiPredicate<PermissionAction, T>> predicates) implements BiPredicate<PermissionAction, T> {

    @Override
    public boolean test(PermissionAction action, T t) {
        return predicates().stream().anyMatch(predicate -> predicate.test(action, t));
    }
}
