package de.derfrzocker.anime.calendar.server.model.domain.season;

import java.time.Month;

public enum Season {

    WINTER(Month.JANUARY, Month.MARCH),
    SPRING(Month.APRIL, Month.JUNE),
    SUMMER(Month.JULY, Month.SEPTEMBER),
    FALL(Month.OCTOBER, Month.DECEMBER);

    private final Month fromInclusive;
    private final Month toInclusive;

    Season(Month fromInclusive, Month toInclusive) {
        this.fromInclusive = fromInclusive;
        this.toInclusive = toInclusive;
    }

    public Season nextSeason() {
        return switch (this) {
            case WINTER -> SPRING;
            case SPRING -> SUMMER;
            case SUMMER -> FALL;
            case FALL -> WINTER;
        };
    }

    public Season previousSeason() {
        return switch (this) {
            case WINTER -> FALL;
            case FALL -> SUMMER;
            case SUMMER -> SPRING;
            case SPRING -> WINTER;
        };
    }

    public int nextYear(int current) {
        if (this == FALL) {
            return current + 1;
        }

        return current;
    }

    public int previousYear(int current) {
        if (this == WINTER) {
            return current - 1;
        }

        return current;
    }

    public Month getToInclusive() {
        return toInclusive;
    }

    public Month getFromInclusive() {
        return fromInclusive;
    }

    public static Season getSeason(Month month) {
        for (Season season : Season.values()) {
            if (season.getFromInclusive().getValue() > month.getValue()) {
                continue;
            }

            if (season.getToInclusive().getValue() < month.getValue()) {
                continue;
            }

            return season;
        }

        throw new AssertionError("There should always be a season");
    }
}