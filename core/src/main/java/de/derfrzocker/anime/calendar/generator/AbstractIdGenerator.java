package de.derfrzocker.anime.calendar.generator;

import java.security.SecureRandom;
import java.util.Random;
import java.util.function.Predicate;

public abstract class AbstractIdGenerator<I> implements IdGenerator<I> {

    // Don't use O and 0 since in many fonts they look to similar
    private static final String CHARS = "ABCDEFGHIJKLMNPQRSTUVWXYZ123456789";

    @Override
    public I generateId() {
        Random random = new SecureRandom();
        char[] result = new char[idLength()];

        result[0] = idPrefix();

        for (int i = 1; i < result.length; i++) {
            result[i] = CHARS.charAt(random.nextInt(CHARS.length()));
        }

        return wrap(new String(result));
    }

    @Override
    public I generateId(Predicate<I> alreadyUsed) {
        I id;

        do {
            id = generateId();
        } while (alreadyUsed.test(id));

        return id;
    }

    protected abstract I wrap(String raw);

    protected abstract int idLength();

    protected abstract char idPrefix();
}
