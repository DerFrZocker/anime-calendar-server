package de.derfrzocker.anime.calendar.server.rest;

import de.derfrzocker.anime.calendar.server.model.core.IdType;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.exception.UnauthenticatedException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import java.time.Instant;

@RequestScoped
public class UserSecurityProvider {

    private static final UserId SECURITY_CONTEXT_USER_ID = new UserId("USECURITYC");

    private static final String USER_ID_ROLE_PREFIX = "User_";
    private static final String NOT_ALLOWED = "Cannot access data.";

    @Context
    SecurityContext securityContext;

    public UserId getCurrentUserId() throws UnauthenticatedException {
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

    public void ensureAccessToUserData(UserId id) throws UnauthenticatedException {
        if (!hasAccessToUserData(id)) {
            throw new UnauthenticatedException(NOT_ALLOWED);
        }
    }

    public RequestContext createRequestContext() throws UnauthenticatedException {
        return new RequestContext(getCurrentUserId(), Instant.now());
    }

    public RequestContext createSecurityContext() {
        return new RequestContext(SECURITY_CONTEXT_USER_ID, Instant.now());
    }

    private boolean hasAccessToUserData(UserId id) {
        return this.securityContext.isUserInRole(USER_ID_ROLE_PREFIX + id.raw());
    }

    private boolean isValidUserId(UserId id) {
        if (id.raw() == null) {
            return false;
        }

        if (id.raw().length() != UserId.ID_LENGTH) {
            return false;
        }

        if (id.raw().charAt(0) != IdType.USER.prefix()) {
            return false;
        }

        for (int i = 0; i < id.raw().length(); i++) {
            char c = id.raw().charAt(i);
            if (c >= 'A' && c <= 'Z' && c != 'O') {
                continue;
            }
            if (c >= '1' && c <= '9') {
                continue;
            }
            return false;
        }

        return true;
    }
}