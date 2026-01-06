package de.derfrzocker.anime.calendar.core.user;

import de.derfrzocker.anime.calendar.core.exception.InvalidValueFormatException;
import de.derfrzocker.anime.calendar.core.util.ValidatorUtil;
import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public record UserId(@NotNull String raw) {

    public static final int ID_LENGTH = 10;
    public static final char ID_PREFIX = 'U';

    public UserId {
        validate(raw, () -> new InvalidValueFormatException("UserId is invalid."));
    }

    @NotNull
    public static UserId of(@NotNull String raw) {
        return new UserId(raw);
    }

    @NotNull
    public static <E extends Throwable> UserId of(@NotNull String raw, @NotNull Supplier<E> throwable) throws E {
        validate(raw, throwable);

        return new UserId(raw);
    }

    private static <E extends Throwable> void validate(@NotNull String raw, @NotNull Supplier<E> throwable) throws E {
        Objects.requireNonNull(raw, "Raw value should not be null.");

        if (!ValidatorUtil.isValidId(raw, ID_LENGTH, ID_PREFIX)) {
            throw throwable.get();
        }
    }
}
