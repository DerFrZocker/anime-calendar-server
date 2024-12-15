package de.derfrzocker.anime.calendar.server.model.domain.permission;

import java.util.Map;

public record ObjectPermission<T>(Map<PermissionType, PermissionRule<T>> permissionRules,
                                  PermissionActionRule<T> actionRules) {

    public boolean hasPermission(PermissionType type, T object) {
        PermissionRule<T> rule = permissionRules().get(type);
        if (rule == null || rule.permissionChecker() == null) {
            return false;
        }

        return rule.permissionChecker().test(object);
    }

    public boolean hasPermission(PermissionAction action, T object) {
        if (actionRules() == null || actionRules().permissionChecker() == null) {
            return false;
        }

        return actionRules().permissionChecker().test(action, object);
    }
}
