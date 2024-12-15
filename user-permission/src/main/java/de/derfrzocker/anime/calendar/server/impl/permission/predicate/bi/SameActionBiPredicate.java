package de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi;

import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import java.util.Objects;
import java.util.function.BiPredicate;

public record SameActionBiPredicate<T>(PermissionAction action) implements BiPredicate<PermissionAction, T> {

    @Override
    public boolean test(PermissionAction action, T toTest) {
        return Objects.equals(action(), action);
    }
}
