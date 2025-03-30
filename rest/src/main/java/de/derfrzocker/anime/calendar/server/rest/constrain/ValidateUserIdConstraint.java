package de.derfrzocker.anime.calendar.server.rest.constrain;

import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.validation.IdValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateUserIdConstraint implements ConstraintValidator<ValidateUserId, UserId> {

    @Override
    public boolean isValid(UserId value, ConstraintValidatorContext context) {
        return IdValidator.isValid(value);
    }
}
