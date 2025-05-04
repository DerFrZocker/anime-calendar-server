package de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data;

import de.derfrzocker.anime.calendar.core.season.Season;

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
