package de.derfrzocker.anime.calendar.collect.syoboi;

import java.time.Instant;

public record ScheduleData(TID tid, Channel channel, int episode, Instant startTime, Instant endTime) {
}
