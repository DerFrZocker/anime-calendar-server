package de.derfrzocker.anime.calendar.utils;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import de.derfrzocker.anime.calendar.server.model.core.Id;
import de.derfrzocker.anime.calendar.server.model.core.IdType;
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
        assertNotNull(id.raw());
        assertEquals(10, id.raw().length());

        for (int i = 0; i < 10; i++) {
            char c = id.raw().charAt(i);
            assertTrue((c >= 'A' && c <= 'Z' && c != 'O') || (c >= '1' && c <= '9'),
                    format("Id is not a Uppercase letter '%c' at index '%d'", c, i));
        }

        assertEquals(prefix, id.raw().charAt(0));
        assertNotNull(id.idType());
        assertSame(idType, id.idType());
    }
}
