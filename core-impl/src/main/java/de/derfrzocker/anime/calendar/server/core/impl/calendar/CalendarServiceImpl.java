package de.derfrzocker.anime.calendar.server.core.impl.calendar;

import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarDao;
import de.derfrzocker.anime.calendar.server.core.api.calendar.CalendarService;
import de.derfrzocker.anime.calendar.server.core.impl.util.StringGenerator;
import de.derfrzocker.anime.calendar.server.model.core.calendar.AnimeOverrideID;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarKey;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.AnimeOverride;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.AnimeOverrideCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.Calendar;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarChangeData;
import de.derfrzocker.anime.calendar.server.model.domain.calendar.CalendarCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.event.calendar.PostCalendarCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.calendar.PreCalendarCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.exception.CalendarNotFoundException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import java.util.Optional;

@RequestScoped
public class CalendarServiceImpl implements CalendarService {

    @Inject
    CalendarDao calendarDao;
    @Inject
    Event<PreCalendarCreateEvent> preCalendarCreateEventBus;
    @Inject
    Event<PostCalendarCreateEvent> postCalendarCreateEventBus;

    @Override
    public Optional<Calendar> getById(CalendarId id) {
        return calendarDao.getById(id);
    }

    @Override
    public Calendar createWithUser(UserId owner) {
        CalendarId id = createNewCalendarId();
        CalendarKey key = StringGenerator.generateCalendarKey(id);

        CalendarCreateData calendarCreateData = new CalendarCreateData(id, key, owner);

        preCalendarCreateEventBus.fire(new PreCalendarCreateEvent(calendarCreateData));

        Calendar calendar = calendarDao.createWithData(calendarCreateData);

        postCalendarCreateEventBus.fire(new PostCalendarCreateEvent(calendar, calendarCreateData));

        return calendar;
    }

    @Override
    public Calendar createAnimeOverride(CalendarId id, AnimeOverrideCreateData animeOverrideCreateData) throws
                                                                                                        CalendarNotFoundException {
        Calendar calendar = getById(id).orElseThrow(CalendarNotFoundException.withId(id));
        AnimeOverrideID animeOverrideID = createAnimOverrideId(calendar);

        AnimeOverride animeOverride = AnimeOverride.from(animeOverrideID, animeOverrideCreateData);

        return calendarDao.updateWithChangeData(calendar, CalendarChangeData.addAnimeOverride(animeOverride));
    }

    private CalendarId createNewCalendarId() {
        CalendarId calendarId;
        do {
            calendarId = StringGenerator.generateCalendarId();
        } while (getById(calendarId).isPresent());

        return calendarId;
    }

    private AnimeOverrideID createAnimOverrideId(Calendar calendar) {
        AnimeOverrideID animeOverrideID;
        do {
            animeOverrideID = StringGenerator.generateAnimeOverrideId();
        } while (calendar.animeOverrides().containsKey(animeOverrideID));

        return animeOverrideID;
    }
}
