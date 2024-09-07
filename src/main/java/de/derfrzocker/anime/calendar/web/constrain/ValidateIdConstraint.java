/*
 * MIT License
 *
 * Copyright (c) 2022 Marvin (DerFrZocker)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.derfrzocker.anime.calendar.web.constrain;

import de.derfrzocker.anime.calendar.api.Id;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateIdConstraint implements ConstraintValidator<ValidateId, Id> {

    @Override
    public boolean isValid(Id value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value.id() == null) {
            return false;
        }

        if (value.idType() == null) {
            return false;
        }

        if (value.id().length() != Id.ID_LENGTH) {
            return false;
        }

        if (value.id().charAt(0) != value.idType().prefix()) {
            return false;
        }

        for (char c : value.id().toCharArray()) {
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
