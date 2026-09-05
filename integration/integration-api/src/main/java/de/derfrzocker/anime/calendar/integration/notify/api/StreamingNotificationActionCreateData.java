package de.derfrzocker.anime.calendar.integration.notify.api;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;

public record StreamingNotificationActionCreateData(AnimeId animeId, int orgEpisodeIndex) {

}
