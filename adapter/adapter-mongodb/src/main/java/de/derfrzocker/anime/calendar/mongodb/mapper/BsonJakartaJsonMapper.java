package de.derfrzocker.anime.calendar.mongodb.mapper;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import org.bson.BsonArray;
import org.bson.BsonBoolean;
import org.bson.BsonDecimal128;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;

import java.util.Map;

public final class BsonJakartaJsonMapper {

    private BsonJakartaJsonMapper() {
    }

    public static JsonValue toJson(BsonValue value) {
        return switch (value.getBsonType()) {
            case ARRAY -> toJson(value.asArray());
            case DOCUMENT -> toJson(value.asDocument());
            case STRING -> toJson(value.asString());
            case DECIMAL128 -> toJson(value.asDecimal128());
            case BOOLEAN -> toJson(value.asBoolean());
            case NULL -> JsonValue.NULL;
            default -> throw new IllegalStateException("Unexpected value: " + value.getBsonType());
        };
    }

    public static JsonArray toJson(BsonArray array) {
        JsonArrayBuilder builder = Json.createArrayBuilder();

        for (BsonValue value : array) {
            builder.add(toJson(value));
        }

        return builder.build();
    }

    public static JsonObject toJson(BsonDocument bsonDocument) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        for (Map.Entry<String, BsonValue> entry : bsonDocument.entrySet()) {
            builder.add(entry.getKey(), toJson(entry.getValue()));
        }

        return builder.build();
    }

    public static JsonString toJson(BsonString string) {
        return Json.createValue(string.getValue());
    }

    public static JsonNumber toJson(BsonDecimal128 number) {
        return Json.createValue(number.getValue().bigDecimalValue());
    }

    public static JsonValue toJson(BsonBoolean bsonBoolean) {
        if (bsonBoolean.getValue()) {
            return JsonValue.TRUE;
        } else {
            return JsonValue.FALSE;
        }
    }
}
