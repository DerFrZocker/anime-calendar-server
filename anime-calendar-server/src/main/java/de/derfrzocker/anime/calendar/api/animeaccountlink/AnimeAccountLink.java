package de.derfrzocker.anime.calendar.api.animeaccountlink;

import de.derfrzocker.anime.calendar.api.calendar.CalendarId;
import de.derfrzocker.anime.calendar.api.user.UserId;
import java.util.Set;

public record AnimeAccountLink(AnimeAccountLinkId animeAccountLinkId, UserId ownerId, AnimeAccountType animeAccountType, Set<CalendarId> linkedCalendar) {
}
