package de.derfrzocker.anime.calendar.collect.syoboi;

import java.util.Map;

public record TitleMediumResponse(Map<String, Data> Titles) {

    public record Data(String TID, String Title, String FirstCh, String FirstYear, String FirstMonth, String FirstEndYear, String FirstEndMonth) {}
}
