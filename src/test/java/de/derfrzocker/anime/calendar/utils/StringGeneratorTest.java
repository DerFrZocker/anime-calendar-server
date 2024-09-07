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

package de.derfrzocker.anime.calendar.utils;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import de.derfrzocker.anime.calendar.api.Id;
import de.derfrzocker.anime.calendar.api.IdType;
import org.junit.jupiter.api.RepeatedTest;

public class StringGeneratorTest {

    @RepeatedTest(100)
    public void testGenerateAnimeId() {
        test(StringGenerator.generateAnimeId(), IdType.ANIME, 'A');
    }

    @RepeatedTest(100)
    public void testGenerateUserId() {
        test(StringGenerator.generateUserId(), IdType.USER, 'U');
    }

    @RepeatedTest(100)
    public void testGenerateCalendarId() {
        test(StringGenerator.generateCalendarId(), IdType.CALENDAR, 'C');
    }

    @RepeatedTest(100)
    public void testGenerateAnimeAccountLinkId() {
        test(StringGenerator.generateAnimeAccountLink(), IdType.ANIME_ACCOUNT_LINK, 'L');
    }

    private void test(Id id, IdType idType, char prefix) {
        assertNotNull(id);
        assertNotNull(id.id());
        assertEquals(10, id.id().length());

        for (int i = 0; i < 10; i++) {
            char c = id.id().charAt(i);
            assertTrue((c >= 'A' && c <= 'Z' && c != 'O') || (c >= '1' && c <= '9'),
                    format("Id is not a Uppercase letter '%c' at index '%d'", c, i));
        }

        assertEquals(prefix, id.id().charAt(0));
        assertNotNull(id.idType());
        assertSame(idType, id.idType());
    }
}
