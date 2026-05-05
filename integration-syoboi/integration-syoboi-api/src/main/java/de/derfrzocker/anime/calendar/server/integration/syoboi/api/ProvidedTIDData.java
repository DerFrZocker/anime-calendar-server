package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

import java.time.YearMonth;
import java.util.List;

public record ProvidedTIDData(
        TID tid, String title, YearMonth firstStart, YearMonth firstEnd, List<ChannelId> firstChannelIds) {

}
