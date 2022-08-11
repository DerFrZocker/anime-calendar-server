package de.derfrzocker.anime.calendar.server.calendar;

import de.derfrzocker.anime.calendar.server.Region;
import de.derfrzocker.anime.calendar.server.provider.AnimeUserInfoProvider;

public record CalendarOptions(String rawParameters, AnimeUserInfoProvider userInfoProvider, String userId, Region region, boolean japanAiringTime) {
}
