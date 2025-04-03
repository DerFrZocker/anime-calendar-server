package de.derfrzocker.anime.calendar.server.impl.permission.change;

import de.derfrzocker.anime.calendar.core.util.Change;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.OrPredicate;
import de.derfrzocker.anime.calendar.server.model.domain.permission.ObjectPermission;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionActionRule;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionRule;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class PermissionRulePredicateAddOrChange<T> implements Change<ObjectPermission<T>> {

    public static <T> PermissionRulePredicateAddOrChange<T> of(PermissionType type, Predicate<T> predicate) {
        return new PermissionRulePredicateAddOrChange<>(type, predicate);
    }

    private final PermissionType type;
    private final Predicate<T> predicate;

    private PermissionRulePredicateAddOrChange(PermissionType type, Predicate<T> predicate) {
        this.type = type;
        this.predicate = predicate;
    }

    @Override
    public ObjectPermission<T> apply(ObjectPermission<T> current) {
        Map<PermissionType, PermissionRule<T>> permission = new EnumMap<>(PermissionType.class);
        PermissionActionRule<T> actionRule = null;

        if (current != null) {
            if (current.permissionRules() != null) {
                permission.putAll(current.permissionRules());
            }

            actionRule = current.actionRules();
        }

        PermissionRule<T> rule = permission.get(this.type);

        if (rule == null || rule.permissionChecker() == null) {
            rule = new PermissionRule<>(this.predicate);
        } else {
            List<Predicate<T>> orPredicates = new ArrayList<>();
            orPredicates.add(this.predicate);
            if (rule.permissionChecker() instanceof OrPredicate<T>(List<Predicate<T>> predicates)) {
                orPredicates.addAll(predicates);
            } else {
                orPredicates.add(rule.permissionChecker());
            }

            rule = new PermissionRule<>(new OrPredicate<>(Collections.unmodifiableList(orPredicates)));
        }

        permission.put(this.type, rule);

        return new ObjectPermission<>(Collections.unmodifiableMap(permission), actionRule);
    }
}
