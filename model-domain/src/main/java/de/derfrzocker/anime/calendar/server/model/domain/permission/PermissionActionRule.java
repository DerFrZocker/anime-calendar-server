package de.derfrzocker.anime.calendar.server.model.domain.permission;

import java.util.function.BiPredicate;

public record PermissionActionRule<T>(BiPredicate<PermissionAction, T> permissionChecker) {

}
