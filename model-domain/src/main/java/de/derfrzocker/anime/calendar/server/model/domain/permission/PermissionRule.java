package de.derfrzocker.anime.calendar.server.model.domain.permission;

import java.util.function.Predicate;

public record PermissionRule<T>(Predicate<T> permissionChecker) {

}
