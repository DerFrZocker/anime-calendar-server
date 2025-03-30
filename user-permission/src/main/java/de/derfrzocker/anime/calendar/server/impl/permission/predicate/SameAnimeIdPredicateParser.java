package de.derfrzocker.anime.calendar.server.impl.permission.predicate;

import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PredicateParser;
import java.util.HashMap;
import java.util.Map;

public class SameAnimeIdPredicateParser implements PredicateParser<SameAnimeIdPredicate> {

    private static final String ANIME_ID = "id";

    @Override
    public SameAnimeIdPredicate decode(Map<String, Object> values) {
        String value = (String) values.get(ANIME_ID);

        if (value == null) {
            return new SameAnimeIdPredicate(null);
        }

        return new SameAnimeIdPredicate(new AnimeId(value));
    }

    @Override
    public Map<String, Object> encode(SameAnimeIdPredicate predicate) {
        Map<String, Object> values = new HashMap<>();

        values.put(ANIME_ID, predicate.id() == null ? null : predicate.id().raw());

        return values;
    }
}
