package de.derfrzocker.anime.calendar.server.impl.permission.change.bi;

import de.derfrzocker.anime.calendar.core.util.Change;
import de.derfrzocker.anime.calendar.server.impl.permission.predicate.bi.OrBiPredicate;
import de.derfrzocker.anime.calendar.server.model.domain.permission.ObjectPermission;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionActionRule;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionRule;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

public class PermissionActionRuleBiPredicateAddOrChange<T> implements Change<ObjectPermission<T>> {

    public static <T> PermissionActionRuleBiPredicateAddOrChange<T> of(BiPredicate<PermissionAction, T> predicate) {
        return new PermissionActionRuleBiPredicateAddOrChange<>(predicate);
    }

    private final BiPredicate<PermissionAction, T> predicate;

    private PermissionActionRuleBiPredicateAddOrChange(BiPredicate<PermissionAction, T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public ObjectPermission<T> apply(ObjectPermission<T> current) {
        Map<PermissionType, PermissionRule<T>> permission = null;
        PermissionActionRule<T> actionRule = null;

        if (current != null) {
            permission = current.permissionRules();
            actionRule = current.actionRules();
        }

        if (actionRule == null || actionRule.permissionChecker() == null) {
            actionRule = new PermissionActionRule<>(this.predicate);
        } else {
            List<BiPredicate<PermissionAction, T>> orPredicates = new ArrayList<>();
            orPredicates.add(this.predicate);
            if (actionRule.permissionChecker() instanceof OrBiPredicate<T>(
                    List<BiPredicate<PermissionAction, T>> predicates
            )) {
                orPredicates.addAll(predicates);
            } else {
                orPredicates.add(actionRule.permissionChecker());
            }

            actionRule = new PermissionActionRule<>(new OrBiPredicate<>(Collections.unmodifiableList(orPredicates)));
        }

        return new ObjectPermission<>(permission, actionRule);
    }
}
