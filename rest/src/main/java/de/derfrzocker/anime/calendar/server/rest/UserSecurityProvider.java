package de.derfrzocker.anime.calendar.server.rest;

import de.derfrzocker.anime.calendar.server.core.api.permission.UserPermissionService;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.calendar.CalendarId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.exception.UnauthenticatedException;
import de.derfrzocker.anime.calendar.server.model.domain.permission.ObjectPermission;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionAction;
import de.derfrzocker.anime.calendar.server.model.domain.permission.PermissionType;
import de.derfrzocker.anime.calendar.server.model.domain.permission.UserPermission;
import de.derfrzocker.anime.calendar.server.validation.IdValidator;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import java.time.Instant;
import java.util.function.Function;

@RequestScoped
public class UserSecurityProvider {

    private static final UserId SECURITY_CONTEXT_USER_ID = new UserId("USECURITYC");

    private static final String USER_ID_ROLE_PREFIX = "User_";

    @Inject
    UserPermissionService permissionService;
    @Context
    SecurityContext securityContext;

    public UserId getCurrentUserId() {
        if (this.securityContext == null) {
            throw new UnauthenticatedException();
        }

        if (this.securityContext.getUserPrincipal() == null) {
            throw new UnauthenticatedException();
        }

        if (this.securityContext.getUserPrincipal().getName() == null) {
            throw new UnauthenticatedException();
        }

        UserId id = new UserId(this.securityContext.getUserPrincipal().getName());

        if (!isValidUserId(id)) {
            throw new UnauthenticatedException();
        }

        return id;
    }

    public boolean hasAccessToUserData(UserId id) {
        return this.securityContext.isUserInRole(USER_ID_ROLE_PREFIX + id.raw());
    }

    public boolean hasPermission(UserId id, PermissionType type, RequestContext context) {
        return hasPermission(id, type, UserPermission::user, context);
    }

    public boolean hasPermission(CalendarId id, PermissionType type, RequestContext context) {
        return hasPermission(id, type, UserPermission::calendar, context);
    }

    public boolean hasPermission(AnimeId id, PermissionType type, RequestContext context) {
        return hasPermission(id, type, UserPermission::anime, context);
    }

    public boolean hasPermission(UserId id, PermissionAction action, RequestContext context) {
        return hasPermission(id, action, UserPermission::user, context);
    }

    public boolean hasPermission(CalendarId id, PermissionAction action, RequestContext context) {
        return hasPermission(id, action, UserPermission::calendar, context);
    }

    public boolean hasPermission(AnimeId id, PermissionAction action, RequestContext context) {
        return hasPermission(id, action, UserPermission::anime, context);
    }

    public <T> boolean hasPermission(T object,
                                     PermissionType type,
                                     Function<UserPermission, ObjectPermission<T>> mapper,
                                     RequestContext context) {
        return this.permissionService.getById(getCurrentUserId(), context)
                                     .map(mapper)
                                     .map(permission -> permission.hasPermission(type, object))
                                     .orElse(false);
    }

    public <T> boolean hasPermission(T object,
                                     PermissionAction action,
                                     Function<UserPermission, ObjectPermission<T>> mapper,
                                     RequestContext context) {
        return this.permissionService.getById(getCurrentUserId(), context)
                                     .map(mapper)
                                     .map(permission -> permission.hasPermission(action, object))
                                     .orElse(false);
    }

    public RequestContext createRequestContext() {
        return new RequestContext(getCurrentUserId(), Instant.now());
    }

    public RequestContext createSecurityContext() {
        return new RequestContext(SECURITY_CONTEXT_USER_ID, Instant.now());
    }

    private boolean isValidUserId(UserId id) {
        return IdValidator.isValid(id);
    }
}
