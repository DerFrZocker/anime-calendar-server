package de.derfrzocker.anime.calendar.external.myanimylist.exception;

public class UnknownException extends AnimeCalendarException {

    private static final String ERROR_CODE = "UNKNOWN";
    private static final String PUBLIC_MESSAGE = "An unknown error occurred, please try again later.";

    public UnknownException(String message, Throwable cause) {
        super(PUBLIC_MESSAGE, message, ERROR_CODE, cause);
    }
}
