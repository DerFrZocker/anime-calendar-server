package de.derfrzocker.anime.calendar.external.schedule;

import de.derfrzocker.anime.calendar.external.ExternalAnimeId;

import java.time.Instant;

public record ExternalScheduleEntry(
        ExternalAnimeId animeId, ExternalChannelId channelId, int episode, Instant startTime, Instant endTime) {

}
