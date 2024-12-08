package de.derfrzocker.anime.calendar.web.constrain;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarKey;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateCalendarKeyConstraint implements ConstraintValidator<ValidateCalendarKey, CalendarKey> {

    @Override
    public boolean isValid(CalendarKey value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value.raw() == null) {
            return false;
        }

        if (value.raw().length() != CalendarKey.CALENDAR_KEY_LENGTH) {
            return false;
        }

        if (value.raw().charAt(0) != CalendarId.ID_PREFIX) {
            return false;
        }

        if (value.raw().charAt(CalendarId.ID_LENGTH) != CalendarKey.KEY_PREFIX_CHAR) {
            return false;
        }

        for (char c : value.raw().toCharArray()) {
            if (c >= 'A' && c <= 'Z' && c != 'O') {
                continue;
            }
            if (c >= '1' && c <= '9') {
                continue;
            }
            return false;
        }

        return true;
    }
}
