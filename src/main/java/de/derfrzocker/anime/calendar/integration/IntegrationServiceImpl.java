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

package de.derfrzocker.anime.calendar.integration;

import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.Region;
import de.derfrzocker.anime.calendar.api.anime.AnimeId;
import de.derfrzocker.anime.calendar.api.calendar.CalendarService;
import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeDao;
import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.api.integration.IntegrationId;
import de.derfrzocker.anime.calendar.api.integration.IntegrationService;
import de.derfrzocker.anime.calendar.api.integration.IntegrationUserDao;
import de.derfrzocker.anime.calendar.api.integration.IntegrationUserId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.inject.Inject;
import java.util.Set;
import net.fortuna.ical4j.model.Calendar;

@ApplicationScoped
public class IntegrationServiceImpl implements IntegrationService {

    @Inject
    IntegrationAnimeDao integrationAnimeDao;
    @Inject
    Instance<IntegrationUserDao> integrationUserDao;
    @Inject
    CalendarService calendarService;

    @Override
    public Calendar getCalendar(IntegrationId integrationId, IntegrationUserId userId) {
        Set<IntegrationAnimeId> integrationAnimeIds = integrationUserDao.select(NamedLiteral.of(integrationId.id())).get().getUserIds(userId);
        return getCalendar(integrationId, integrationAnimeIds);
    }

    @Override
    public Calendar getCalendar(IntegrationId integrationId, Set<IntegrationAnimeId> integrationAnimeIds) {
        Set<AnimeId> animeIds = integrationAnimeDao.getAnimeIds(integrationId, integrationAnimeIds);

        return calendarService.buildCalendar(animeIds, new AnimeOptions(Region.DE_DE, true));
    }
}
