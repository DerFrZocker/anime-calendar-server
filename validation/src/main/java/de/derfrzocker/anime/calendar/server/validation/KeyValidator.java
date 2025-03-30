package de.derfrzocker.anime.calendar.server.validation;

import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.server.validation.exception.InvalidKeyException;

public final class KeyValidator {

    public static boolean isValid(CalendarKey key) {
        if (key == null || key.raw() == null) {
            return false;
        }

        return isValid(key.raw(),
                       CalendarKey.KEY_LENGTH,
                       CalendarId.ID_PREFIX,
                       CalendarKey.KEY_PREFIX_CHAR,
                       CalendarId.ID_LENGTH);
    }

    public static void validate(CalendarKey key) {
        if (isValid(key)) {
            return;
        }

        throw InvalidKeyException.with(key);
    }

    private static boolean isValid(String input, int length, char idPrefix, char keyPrefix, int keyPrefixIndex) {
        if (!StringValidator.isValidLength(input, length)) {
            return false;
        }

        if (!StringValidator.isValidStartChar(input, idPrefix)) {
            return false;
        }

        if (!StringValidator.isValidChar(input, keyPrefix, keyPrefixIndex)) {
            return false;
        }

        return StringValidator.isValidChars(input);
    }

    private KeyValidator() {

    }
}
