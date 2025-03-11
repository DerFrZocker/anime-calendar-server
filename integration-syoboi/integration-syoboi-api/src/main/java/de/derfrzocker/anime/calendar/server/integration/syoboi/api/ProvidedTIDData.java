package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

import java.time.YearMonth;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public record ProvidedTIDData(TID tid, String title, YearMonth firstStart, @Nullable YearMonth firstEnd,
                              List<Channel> firstChannels) {

}
