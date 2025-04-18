package de.derfrzocker.anime.calendar.server.layer2.parser;

import de.derfrzocker.anime.calendar.core.exception.InconsistentDataException;
import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import de.derfrzocker.anime.calendar.server.layer2.api.LayerConfig;
import java.time.Instant;
import java.time.Period;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

public abstract class AbstractLayerConfigParser<T extends LayerConfig> implements LayerConfigParser<T> {

    private static final String PARSER_EXCEPTION_MESSAGE = "Value '%s' for key '%s' in '%s' could not be parsed to an '%s'.";
    private static final String LAYER_KEY = "layer-key";

    protected LayerKey decodeLayerKey(Map<String, Object> values) {
        String value = decodeString(values, LAYER_KEY);

        return new LayerKey(value);
    }

    protected int decodeInt(Map<String, Object> values, String key) {
        Object value = values.get(key);

        ensureNotNull(values, key, value);

        return parseInt(values, key, value);
    }

    protected int decodeInt(Map<String, Object> values, String key, int defaultValue) {
        Object value = values.get(key);

        if (value == null) {
            return defaultValue;
        }

        return parseInt(values, key, value);
    }

    protected String decodeString(Map<String, Object> values, String key) {
        Object value = values.get(key);

        ensureNotNull(values, key, value);

        return String.valueOf(value);
    }

    protected String decodeString(Map<String, Object> values, String key, String defaultValue) {
        Object value = values.get(key);

        if (value == null) {
            return defaultValue;
        }

        return String.valueOf(value);
    }

    protected Stream<String> decodeStringStream(Map<String, Object> values, String key) {
        Object value = values.get(key);

        ensureNotNull(values, key, value);

        return parseStream(values, key, value).map(String::valueOf);
    }

    protected Instant decodeInstant(Map<String, Object> values, String key) {
        String value = decodeString(values, key);

        return Instant.parse(value);
    }

    protected Period decodePeriod(Map<String, Object> values, String key) {
        String value = decodeString(values, key);

        return Period.parse(value);
    }

    protected void encodeLayerKey(Map<String, Object> values, LayerKey value) {
        ensureNotNull(LAYER_KEY, value);

        values.put(LAYER_KEY, value.raw());
    }

    protected void encodeInt(Map<String, Object> values, String key, int value) {
        values.put(key, value);
    }

    protected void encodeString(Map<String, Object> values, String key, String value) {
        ensureNotNull(key, value);

        values.put(key, value);
    }

    protected void encodeString(Map<String, Object> values, String key, String value, String defaultValue) {
        if (value == null) {
            values.put(key, defaultValue);
        } else {
            values.put(key, value);
        }
    }

    protected void encodeStringStream(Map<String, Object> values, String key, Stream<String> value) {
        ensureNotNull(key, value);

        try (value) {
            values.put(key, value.toList());
        }
    }

    protected void encodeInstant(Map<String, Object> values, String key, Instant value) {
        ensureNotNull(key, value);

        values.put(key, value.toString());
    }

    protected void encodePeriod(Map<String, Object> values, String key, Period value) {
        ensureNotNull(key, value);

        values.put(key, value.toString());
    }

    private int parseInt(Map<String, Object> values, String key, Object value) {
        if (value instanceof Number number) {
            return number.intValue();
        }

        try {
            return Integer.parseInt(String.valueOf(value));
        } catch (NumberFormatException e) {
            throw InconsistentDataException.from(PARSER_EXCEPTION_MESSAGE.formatted(value, key, values, "int"), e)
                                           .get();
        }
    }

    private Stream<Object> parseStream(Map<String, Object> values, String key, Object value) {
        if (value instanceof Collection<?> collection) {
            return collection.stream().map(Object.class::cast);
        }

        throw InconsistentDataException.from(PARSER_EXCEPTION_MESSAGE.formatted(value, key, values, "Stream")).get();
    }

    private void ensureNotNull(String key, Object value) {
        if (value == null) {
            throw InconsistentDataException.from("Can not insert null value for key '%s'.".formatted(key)).get();
        }
    }

    private void ensureNotNull(Map<String, Object> values, String key, Object value) {
        if (value == null) {
            throw InconsistentDataException.from("No value found for key '%s' in '%s'.".formatted(key, values)).get();
        }
    }
}
