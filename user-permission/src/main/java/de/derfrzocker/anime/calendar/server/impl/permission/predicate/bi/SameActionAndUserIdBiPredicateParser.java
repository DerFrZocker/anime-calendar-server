package de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi;

import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.permission.BiPredicateParser;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import java.util.HashMap;
import java.util.Map;

public class SameActionAndUserIdBiPredicateParser implements BiPredicateParser<SameActionAndUserIdBiPredicate> {

    private static final String ACTION = "action";
    private static final String USER_ID = "id";

    @Override
    public SameActionAndUserIdBiPredicate decode(Map<String, Object> values) {
        String actionValue = (String) values.get(ACTION);
        String idValue = (String) values.get(USER_ID);

        PermissionAction action = null;
        UserId id = null;

        if (actionValue != null) {
            action = new PermissionAction(actionValue);
        }

        if (idValue != null) {
            id = UserId.of(idValue);
        }

        return new SameActionAndUserIdBiPredicate(action, id);
    }

    @Override
    public Map<String, Object> encode(SameActionAndUserIdBiPredicate predicate) {
        Map<String, Object> values = new HashMap<>();

        values.put(ACTION, predicate.action() == null ? null : predicate.action().raw());
        values.put(USER_ID, predicate.id() == null ? null : predicate.id().raw());

        return values;
    }
}
