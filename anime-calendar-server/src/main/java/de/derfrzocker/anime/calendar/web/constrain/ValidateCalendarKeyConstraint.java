package de.derfrzocker.anime.calendar.web.constrain;

import de.derfrzocker.anime.calendar.api.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.server.model.core.Id;
import de.derfrzocker.anime.calendar.server.model.core.IdType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateCalendarKeyConstraint implements ConstraintValidator<ValidateCalendarKey, CalendarKey> {

    @Override
    public boolean isValid(CalendarKey value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value.key() == null) {
            return false;
        }

        if (value.key().length() != CalendarKey.CALENDAR_KEY_LENGTH) {
            return false;
        }

        if (value.key().charAt(0) != IdType.CALENDAR.prefix()) {
            return false;
        }

        if (value.key().charAt(Id.ID_LENGTH) != CalendarKey.KEY_PREFIX_CHAR) {
            return false;
        }

        for (char c : value.key().toCharArray()) {
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
