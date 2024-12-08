package de.derfrzocker.anime.calendar.server.rest.constrain;

import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.validation.IdValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateCalendarIdConstraint implements ConstraintValidator<ValidateCalendarId, CalendarId> {

    @Override
    public boolean isValid(CalendarId value, ConstraintValidatorContext context) {
        return IdValidator.isValid(value);
    }
}
