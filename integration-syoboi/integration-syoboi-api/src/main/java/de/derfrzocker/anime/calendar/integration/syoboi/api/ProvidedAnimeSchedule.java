package de.derfrzocker.anime.calendar.integration.syoboi.api;

import java.time.Instant;

public record ProvidedAnimeSchedule(TID tid, ChannelId channelId, int episode, Instant startTime, Instant endTime) {

}
