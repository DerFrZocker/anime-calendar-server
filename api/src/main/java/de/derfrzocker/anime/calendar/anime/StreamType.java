package de.derfrzocker.anime.calendar.anime;

import de.derfrzocker.anime.calendar.exception.InvalidValueFormatException;
import java.util.Objects;

public record StreamType(String raw) {

    private static final String PUBLIC_MESSAGE = "Stream type must be either 'org', 'sub' or 'dub'.";
    private static final String MESSAGE = PUBLIC_MESSAGE;
    private static final String ERROR_CODE = "INVALID_STREAM_TYPE_FORMAT";

    public static StreamType of(String raw) {
        return new StreamType(raw);
    }

    public StreamType {
        Objects.requireNonNull(raw);
        validate(raw);
    }

    private static void validate(String raw) {

        if (StreamTypes.ORG_RAW.equals(raw)) {
            return;
        }
        if (StreamTypes.SUB_RAW.equals(raw)) {
            return;
        }
        if (StreamTypes.DUB_RAW.equals(raw)) {
            return;
        }

        throw new InvalidValueFormatException(PUBLIC_MESSAGE, MESSAGE, ERROR_CODE);
    }
}
