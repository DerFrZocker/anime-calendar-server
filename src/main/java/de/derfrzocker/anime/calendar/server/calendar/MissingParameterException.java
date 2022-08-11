package de.derfrzocker.anime.calendar.server.calendar;

public class MissingParameterException extends ParameterException{
    public MissingParameterException(String message) {
        super("Missing parameter: " + message);
    }
}
