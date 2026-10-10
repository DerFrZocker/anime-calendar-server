package de.derfrzocker.anime.calendar.external.myanimelist.rest.validation;

import de.derfrzocker.anime.calendar.external.ExternalUserId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateMyAnimeListUsernameConstraint
        implements ConstraintValidator<ValidateMyAnimeListUserId, ExternalUserId> {

    @Override
    public boolean isValid(ExternalUserId value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value.raw() == null) {
            return false;
        }

        if (value.raw().length() < 2) {
            return false;
        }

        if (value.raw().length() > 16) {
            return false;
        }

        for (char c : value.raw().toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                continue;
            }
            if (c >= 'a' && c <= 'z') {
                continue;
            }
            if (c >= '0' && c <= '9') {
                continue;
            }
            if (c == '_' || c == '-') {
                continue;
            }

            return false;
        }

        return true;
    }
}
