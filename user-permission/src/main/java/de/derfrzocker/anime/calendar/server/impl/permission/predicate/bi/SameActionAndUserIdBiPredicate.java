package de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import java.util.Objects;
import java.util.function.BiPredicate;

public record SameActionAndUserIdBiPredicate(PermissionAction action,
                                             UserId id) implements BiPredicate<PermissionAction, UserId> {

    @Override
    public boolean test(PermissionAction action, UserId toTest) {
        return Objects.equals(action(), action) && Objects.equals(id(), toTest);
    }
}
