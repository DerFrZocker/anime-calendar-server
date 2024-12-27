package de.derfrzocker.anime.calendar.server.impl.syoboi.domain;

import java.time.YearMonth;

public record TIDData(TID tid, String title, Channel channel, YearMonth start, YearMonth end, boolean include) {

}
