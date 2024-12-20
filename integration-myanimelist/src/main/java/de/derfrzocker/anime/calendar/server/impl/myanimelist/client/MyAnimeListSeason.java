package de.derfrzocker.anime.calendar.server.impl.myanimelist.client;

import de.derfrzocker.anime.calendar.server.model.domain.season.Season;

public enum MyAnimeListSeason {

    winter, spring, summer, fall;

    public static MyAnimeListSeason from(Season season) {
        return switch (season) {
            case WINTER -> winter;
            case SPRING -> spring;
            case SUMMER -> summer;
            case FALL -> fall;
        };
    }
}
