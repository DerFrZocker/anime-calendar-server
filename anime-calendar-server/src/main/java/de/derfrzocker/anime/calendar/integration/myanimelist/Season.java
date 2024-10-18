package de.derfrzocker.anime.calendar.integration.myanimelist;

enum Season {

    winter,
    spring,
    summer,
    fall;

    Season nextSeason() {
        return switch (this) {
            case winter -> spring;
            case spring -> summer;
            case summer -> fall;
            case fall -> winter;
        };
    }

    Season previousSeason() {
        return switch (this) {
            case winter -> fall;
            case fall -> summer;
            case summer -> spring;
            case spring -> winter;
        };
    }

    int nextYear(int current) {
        if (this == fall) {
            return current + 1;
        }

        return current;
    }

    int previousYear(int current) {
        if (this == winter) {
            return current - 1;
        }

        return current;
    }
}
