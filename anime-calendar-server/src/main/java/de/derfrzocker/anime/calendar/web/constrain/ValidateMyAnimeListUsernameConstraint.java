package de.derfrzocker.anime.calendar.web.constrain;

import de.derfrzocker.anime.calendar.api.integration.IntegrationUserId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateMyAnimeListUsernameConstraint implements ConstraintValidator<ValidateMyAnimeListUsername, IntegrationUserId> {

    @Override
    public boolean isValid(IntegrationUserId value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value.id() == null) {
            return false;
        }

        if (value.id().length() < 2) {
            return false;
        }

        if (value.id().length() > 16) {
            return false;
        }

        for (char c : value.id().toCharArray()) {
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
