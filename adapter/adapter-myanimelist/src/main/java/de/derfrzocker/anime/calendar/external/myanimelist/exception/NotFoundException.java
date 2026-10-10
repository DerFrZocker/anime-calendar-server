package de.derfrzocker.anime.calendar.external.myanimelist.exception;

import de.derfrzocker.anime.calendar.exception.AnimeCalendarException;

public class NotFoundException extends AnimeCalendarException {

    private static final String ERROR_CODE = "NOT_FOUND";

    public NotFoundException(String publicMessage, String message) {
        super(publicMessage, message, ERROR_CODE);
    }
}
