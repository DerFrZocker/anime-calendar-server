package de.derfrzocker.anime.calendar.logic;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class MutinyUtils {

    private MutinyUtils() {

    }

    public static <T, O> Function<Uni<T>, Uni<Tuple2<T, O>>> with(O other) {
        return uni -> uni.map(t -> Tuple2.of(t, other));
    }

    public static <T, C> Function<T, Uni<?>> ifPresent(
            Function<T, Optional<C>> mapper,
            BiFunction<C, T, Uni<?>> ifPresent) {

        return item -> {
            Optional<C> opt = mapper.apply(item);
            if (opt.isPresent()) {
                return ifPresent.apply(opt.get(), item);
            }

            return Uni.createFrom().nullItem();
        };
    }

    public static <T, C> Function<T, Uni<?>> ifEmpty(Function<T, Optional<C>> mapper, Function<T, Uni<?>> ifEmpty) {
        return item -> {
            Optional<C> opt = mapper.apply(item);
            if (opt.isEmpty()) {
                return ifEmpty.apply(item);
            }

            return Uni.createFrom().nullItem();
        };
    }

    public static <T, C, R> Function<T, Uni<? extends R>> optional(
            Function<T, Optional<C>> mapper,
            BiFunction<C, T, Uni<R>> ifPresent,
            Function<T, Uni<R>> ifEmpty) {

        return item -> {
            Optional<C> opt = mapper.apply(item);
            if (opt.isPresent()) {
                return ifPresent.apply(opt.get(), item);
            }

            return ifEmpty.apply(item);
        };
    }
}
