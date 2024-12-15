package de.derfrzocker.anime.calendar.server.core.impl.permission;

import de.derfrzocker.anime.calendar.server.core.api.permission.PredicateParserService;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.AndPredicate;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.AndPredicateParser;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.FixedPredicate;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.FixedPredicateParser;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.OrPredicate;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.OrPredicateParser;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.SameAnimeIdPredicate;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.SameAnimeIdPredicateParser;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.SameCalendarIdPredicate;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.SameCalendarIdPredicateParser;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.SameUserIdPredicate;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.SameUserIdPredicateParser;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi.FixedBiPredicate;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi.FixedBiPredicateParser;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi.OrBiPredicate;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi.OrBiPredicateParser;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi.SameActionAndCalendarIdBiPredicate;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi.SameActionAndCalendarIdBiPredicateParser;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi.SameActionAndUserIdBiPredicate;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi.SameActionAndUserIdBiPredicateParser;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi.SameActionBiPredicate;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi.SameActionBiPredicateParser;
import de.derfrzocker.anime.calendar.server.model.domain.permission.BiPredicateParser;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PredicateParser;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@ApplicationScoped
public class PredicateParserServiceImpl implements PredicateParserService {

    private static final String PREDICATE_KEY = "predicate_key";

    private final Map<Class<?>, PredicateParser<?>> parserByClass = new HashMap<>();
    private final Map<Class<?>, PredicateKey> keyByClass = new HashMap<>();
    private final Map<PredicateKey, PredicateParser<?>> parserByKey = new HashMap<>();
    private final Map<Class<?>, BiPredicateParser<?>> biParserByClass = new HashMap<>();
    private final Map<PredicateKey, BiPredicateParser<?>> biParserByKey = new HashMap<>();

    @Override
    public <T> Predicate<T> decode(Map<String, Object> values) {
        // TODO 2024-12-13: Better exception / error handling
        PredicateKey key = new PredicateKey((String) values.get(PREDICATE_KEY));

        PredicateParser<Predicate<T>> parser = (PredicateParser<Predicate<T>>) this.parserByKey.get(key);

        return parser.decode(values);
    }

    @Override
    public <T> Map<String, Object> encode(Predicate<T> predicate) {
        // TODO 2024-12-13: Better exception / error handling
        PredicateParser<Predicate<T>> parser = (PredicateParser<Predicate<T>>) this.parserByClass.get(predicate.getClass());

        Map<String, Object> result = parser.encode(predicate);
        result.put(PREDICATE_KEY, this.keyByClass.get(predicate.getClass()).raw());

        return result;
    }

    @Override
    public <T> BiPredicate<PermissionAction, T> decodeBi(Map<String, Object> values) {
        // TODO 2024-12-13: Better exception / error handling
        PredicateKey key = new PredicateKey((String) values.get(PREDICATE_KEY));

        BiPredicateParser<BiPredicate<PermissionAction, T>> parser = (BiPredicateParser<BiPredicate<PermissionAction, T>>) this.biParserByKey.get(
                key);

        return parser.decode(values);
    }

    @Override
    public <T> Map<String, Object> encodeBi(BiPredicate<PermissionAction, T> predicate) {
        // TODO 2024-12-13: Better exception / error handling
        BiPredicateParser<BiPredicate<PermissionAction, T>> parser = (BiPredicateParser<BiPredicate<PermissionAction, T>>) this.biParserByClass.get(
                predicate.getClass());

        Map<String, Object> result = parser.encode(predicate);
        result.put(PREDICATE_KEY, this.keyByClass.get(predicate.getClass()).raw());

        return result;
    }

    @PostConstruct
    void registerParsers() {
        registerParser("and", AndPredicate.class, new AndPredicateParser(this::decode, this::encode));
        registerParser("or", OrPredicate.class, new OrPredicateParser(this::decode, this::encode));
        registerParser("same-anime-id", SameAnimeIdPredicate.class, new SameAnimeIdPredicateParser());
        registerParser("same-calendar-id", SameCalendarIdPredicate.class, new SameCalendarIdPredicateParser());
        registerParser("same-user-id", SameUserIdPredicate.class, new SameUserIdPredicateParser());
        registerParser("fixed", FixedPredicate.class, new FixedPredicateParser());

        registerParser("bi-or", OrBiPredicate.class, new OrBiPredicateParser(this::decodeBi, this::encodeBi));
        registerParser("bi-same-action", SameActionBiPredicate.class, new SameActionBiPredicateParser<>());
        registerParser("bi-same-action-and-calendar-id",
                       SameActionAndCalendarIdBiPredicate.class,
                       new SameActionAndCalendarIdBiPredicateParser());
        registerParser("bi-same-action-and-user-id",
                       SameActionAndUserIdBiPredicate.class,
                       new SameActionAndUserIdBiPredicateParser());
        registerParser("bi-fixed", FixedBiPredicate.class, new FixedBiPredicateParser());
    }

    private <T> void registerParser(String key, Class<?> clazz, PredicateParser<?> parser) {
        this.parserByClass.put(clazz, parser);
        this.keyByClass.put(clazz, new PredicateKey(key));
        this.parserByKey.put(new PredicateKey(key), parser);
    }

    private <T> void registerParser(String key, Class<?> clazz, BiPredicateParser<?> parser) {
        this.biParserByClass.put(clazz, parser);
        this.keyByClass.put(clazz, new PredicateKey(key));
        this.biParserByKey.put(new PredicateKey(key), parser);
    }

    private record PredicateKey(String raw) {

    }
}
