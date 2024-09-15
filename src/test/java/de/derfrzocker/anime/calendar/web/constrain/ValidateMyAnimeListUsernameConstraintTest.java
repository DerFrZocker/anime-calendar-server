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

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import de.derfrzocker.anime.calendar.api.integration.IntegrationUserId;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidateMyAnimeListUsernameConstraintTest {

    @ParameterizedTest
    @ValueSource(strings = {"AA", "1234567890123456", "arsti23Arsa"})
    void testCorrectAnimeId(String value) {
        ValidateMyAnimeListUsernameConstraint constraint = new ValidateMyAnimeListUsernameConstraint();

        assertTrue(constraint.isValid(new IntegrationUserId(value), null), format("Username %s should be valid", value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "12345678901234567", "@@@" , "arst#$"})
    void testIncorrectAnimeId(String value) {
        ValidateMyAnimeListUsernameConstraint constraint = new ValidateMyAnimeListUsernameConstraint();

        assertFalse(constraint.isValid(new IntegrationUserId(value), null), format("Username %s should be invalid", value));
    }
}
