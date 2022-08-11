package de.derfrzocker.anime.calendar.server.calendar;

public class InvalidParameterException extends ParameterException{
    public InvalidParameterException(String message) {
        super("Invalid Parameter: " + message);
    }
}
