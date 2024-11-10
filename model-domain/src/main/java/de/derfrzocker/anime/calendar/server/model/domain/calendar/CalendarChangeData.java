package de.derfrzocker.anime.calendar.server.model.domain.calendar;

import de.derfrzocker.anime.calendar.server.model.core.calendar.AnimeOverrideID;
import de.derfrzocker.anime.calendar.server.model.domain.util.Change;
import de.derfrzocker.anime.calendar.server.model.domain.util.MapAddChange;
import java.util.Map;

public record CalendarChangeData(Change<Map<AnimeOverrideID, AnimeOverride>> animeOverrides) {

    public static CalendarChangeData addAnimeOverride(AnimeOverride animeOverride) {
        return new CalendarChangeData(new MapAddChange<>(animeOverride.id(), animeOverride));
    }
}
