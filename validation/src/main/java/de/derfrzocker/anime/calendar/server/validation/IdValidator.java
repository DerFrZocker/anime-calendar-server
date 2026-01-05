package de.derfrzocker.anime.calendar.server.validation;

import de.derfrzocker.anime.calendar.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.validation.exception.InvalidIdException;

public final class IdValidator {

    public static boolean isValid(UserId id) {
        if (id == null || id.raw() == null) {
            return false;
        }

        return isValid(id.raw(), UserId.ID_LENGTH, UserId.ID_PREFIX);
    }

    public static void validate(UserId id) {
        if (isValid(id)) {
            return;
        }

        throw InvalidIdException.with(id);
    }

    public static boolean isValid(CalendarId id) {
        if (id == null || id.raw() == null) {
            return false;
        }

        return isValid(id.raw(), CalendarId.ID_LENGTH, CalendarId.ID_PREFIX);
    }

    public static void validate(CalendarId id) {
        if (isValid(id)) {
            return;
        }

        throw InvalidIdException.with(id);
    }

    private static boolean isValid(String input, int length, char prefix) {
        if (!StringValidator.isValidLength(input, length)) {
            return false;
        }

        if (!StringValidator.isValidStartChar(input, prefix)) {
            return false;
        }

        return StringValidator.isValidChars(input);
    }

    private IdValidator() {

    }
}
