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
import de.derfrzocker.anime.calendar.api.IdType;
import de.derfrzocker.anime.calendar.api.calendar.CalendarKey;
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
