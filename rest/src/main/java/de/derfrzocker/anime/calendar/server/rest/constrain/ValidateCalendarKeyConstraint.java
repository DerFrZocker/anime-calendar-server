package de.derfrzocker.anime.calendar.server.rest.constrain;

import de.derfrzocker.anime.calendar.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.server.validation.KeyValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateCalendarKeyConstraint implements ConstraintValidator<ValidateCalendarKey, CalendarKey> {

    @Override
    public boolean isValid(CalendarKey value, ConstraintValidatorContext context) {
        return KeyValidator.isValid(value);
    }
}
