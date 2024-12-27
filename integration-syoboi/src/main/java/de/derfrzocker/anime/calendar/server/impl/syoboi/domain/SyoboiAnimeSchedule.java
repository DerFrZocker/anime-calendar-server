package de.derfrzocker.anime.calendar.server.impl.syoboi.domain;

import java.time.Instant;

public record SyoboiAnimeSchedule(TID tid, Channel channel, int episode, Instant startTime, Instant endTime) {

}
