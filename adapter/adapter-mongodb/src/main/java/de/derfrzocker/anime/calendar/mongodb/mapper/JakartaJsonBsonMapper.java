package de.derfrzocker.anime.calendar.mongodb.mapper;

import jakarta.json.JsonArray;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import org.bson.BsonArray;
import org.bson.BsonBoolean;
import org.bson.BsonDecimal128;
import org.bson.BsonDocument;
import org.bson.BsonNull;
import org.bson.BsonNumber;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.types.Decimal128;

import java.util.Map;

public final class JakartaJsonBsonMapper {

    private JakartaJsonBsonMapper() {
    }

    public static BsonValue toBson(JsonValue value) {
        return switch (value.getValueType()) {
            case ARRAY -> toBson((JsonArray) value);
            case OBJECT -> toBson((JsonObject) value);
            case STRING -> toBson((JsonString) value);
            case NUMBER -> toBson((JsonNumber) value);
            case TRUE -> BsonBoolean.TRUE;
            case FALSE -> BsonBoolean.FALSE;
            case NULL -> BsonNull.VALUE;
        };
    }

    public static BsonArray toBson(JsonArray array) {
        BsonArray bsonArray = new BsonArray();

        for (JsonValue value : array) {
            bsonArray.add(toBson(value));
        }

        return bsonArray;
    }

    public static BsonDocument toBson(JsonObject object) {
        BsonDocument bsonDocument = new BsonDocument();

        for (Map.Entry<String, JsonValue> entry : object.entrySet()) {
            bsonDocument.append(entry.getKey(), toBson(entry.getValue()));
        }

        return bsonDocument;
    }

    public static BsonString toBson(JsonString string) {
        return new BsonString(string.getString());
    }

    public static BsonNumber toBson(JsonNumber number) {
        return new BsonDecimal128(new Decimal128(number.bigDecimalValue()));
    }
}
