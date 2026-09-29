package de.derfrzocker.anime.calendar.external.myanimylist.exception;

public class NotFoundException extends AnimeCalendarException {

    private static final String ERROR_CODE = "NOT_FOUND";

    public NotFoundException(String publicMessage, String message) {
        super(publicMessage, message, ERROR_CODE);
    }
}
