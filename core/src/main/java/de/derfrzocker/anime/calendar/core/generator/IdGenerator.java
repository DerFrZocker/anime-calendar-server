package de.derfrzocker.anime.calendar.core.generator;

import java.util.function.Predicate;

public interface IdGenerator<I> {

    I generateId();

    I generateId(Predicate<I> alreadyUsed);
}
