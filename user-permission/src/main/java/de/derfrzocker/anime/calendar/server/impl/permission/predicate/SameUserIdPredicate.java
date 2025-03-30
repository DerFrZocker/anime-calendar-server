package de.derfrzocker.anime.calendar.server.impl.permission.predicate;

import de.derfrzocker.anime.calendar.core.user.UserId;
import java.util.Objects;
import java.util.function.Predicate;

public record SameUserIdPredicate(UserId id) implements Predicate<UserId> {

    @Override
    public boolean test(UserId toTest) {
        return Objects.equals(id(), toTest);
    }
}
