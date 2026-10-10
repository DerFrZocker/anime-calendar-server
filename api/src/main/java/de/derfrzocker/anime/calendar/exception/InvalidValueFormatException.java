package de.derfrzocker.anime.calendar.exception;

public class InvalidValueFormatException extends AnimeCalendarException {

    public InvalidValueFormatException(String publicMessage, String message, String errorCode) {
        super(publicMessage, message, errorCode);
    }
}
