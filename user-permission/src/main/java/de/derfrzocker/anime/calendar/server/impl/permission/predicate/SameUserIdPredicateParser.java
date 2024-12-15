package de.derfrzocker.anime.calendar.server.impl.permission.predicate;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PredicateParser;
import java.util.HashMap;
import java.util.Map;

public class SameUserIdPredicateParser implements PredicateParser<SameUserIdPredicate> {

    private static final String USER_ID = "id";

    @Override
    public SameUserIdPredicate decode(Map<String, Object> values) {
        String value = (String) values.get(USER_ID);

        if (value == null) {
            return new SameUserIdPredicate(null);
        }

        return new SameUserIdPredicate(new UserId(value));
    }

    @Override
    public Map<String, Object> encode(SameUserIdPredicate predicate) {
        Map<String, Object> values = new HashMap<>();

        values.put(USER_ID, predicate.id() == null ? null : predicate.id().raw());

        return values;
    }
}
