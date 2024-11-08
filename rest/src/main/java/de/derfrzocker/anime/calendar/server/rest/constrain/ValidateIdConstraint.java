package de.derfrzocker.anime.calendar.server.rest.constrain;

import de.derfrzocker.anime.calendar.server.model.core.Id;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateIdConstraint implements ConstraintValidator<ValidateId, Id> {

    @Override
    public boolean isValid(Id value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value.raw() == null) {
            return false;
        }

        if (value.idType() == null) {
            return false;
        }

        if (value.raw().length() != Id.ID_LENGTH) {
            return false;
        }

        if (value.raw().charAt(0) != value.idType().prefix()) {
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
