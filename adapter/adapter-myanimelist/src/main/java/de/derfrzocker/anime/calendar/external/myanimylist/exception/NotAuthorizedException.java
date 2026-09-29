package de.derfrzocker.anime.calendar.external.myanimylist.exception;

public class NotAuthorizedException extends AnimeCalendarException {

    private static final String ERROR_CODE = "NOT_AUTHORIZED";

    public NotAuthorizedException(String publicMessage, String message) {
        super(publicMessage, message, ERROR_CODE);
    }
}
