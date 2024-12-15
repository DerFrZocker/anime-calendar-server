package de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi;

import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import java.util.function.BiPredicate;

public record FixedBiPredicate<T>(boolean value) implements BiPredicate<PermissionAction, T> {

    @Override
    public boolean test(PermissionAction action, T o) {
        return value();
    }
}
