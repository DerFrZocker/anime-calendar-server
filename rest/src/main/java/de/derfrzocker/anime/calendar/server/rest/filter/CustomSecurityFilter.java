package de.derfrzocker.anime.calendar.server.rest.filter;

import de.derfrzocker.anime.calendar.server.core.api.user.UserService;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.user.UserToken;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class CustomSecurityFilter implements ContainerRequestFilter {

    private static final String USER_ID_ROLE_PREFIX = "User_";

    @Inject
    UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String header = requestContext.getHeaderString("Authorization");
        if (header == null) {
            applyEmptySecurityContext(requestContext);
            return;
        }

        header = header.trim();

        if (!isValidTokenFormat(header)) {
            applyEmptySecurityContext(requestContext);
            return;
        }

        UserToken userToken = new UserToken(header);

        // TODO 2024-12-08: Create Context
        if (!isValidToken(userToken, null)) {
            applyEmptySecurityContext(requestContext);
            return;
        }

        SecurityContext oldContext = requestContext.getSecurityContext();
        requestContext.setSecurityContext(new SecurityContext() {
            private final Principal principal = () -> userToken.userId().raw();

            @Override
            public Principal getUserPrincipal() {
                return principal;
            }

            @Override
            public boolean isUserInRole(String role) {
                if (role.length() != (USER_ID_ROLE_PREFIX.length() + UserId.ID_LENGTH)) {
                    return false;
                }

                if (!role.startsWith(USER_ID_ROLE_PREFIX)) {
                    return false;
                }

                return role.endsWith(userToken.userId().raw());
            }

            @Override
            public boolean isSecure() {
                return oldContext.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return "Token";
            }
        });
    }

    private void applyEmptySecurityContext(ContainerRequestContext requestContext) {
        SecurityContext oldContext = requestContext.getSecurityContext();
        requestContext.setSecurityContext(new SecurityContext() {
            private final Principal principal = () -> "";

            @Override
            public Principal getUserPrincipal() {
                return principal;
            }

            @Override
            public boolean isUserInRole(String role) {
                return false;
            }

            @Override
            public boolean isSecure() {
                return oldContext.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return null;
            }
        });
    }

    private boolean isValidTokenFormat(String token) {
        if (token == null) {
            return false;
        }

        if (token.length() != UserToken.TOKEN_KEY_LENGTH) {
            return false;
        }

        if (token.charAt(0) != UserId.ID_PREFIX) {
            return false;
        }

        if (token.charAt(UserId.ID_LENGTH) != UserToken.TOKEN_PREFIX_CHAR) {
            return false;
        }

        for (int i = 0; i < token.length(); i++) {
            char c = token.charAt(i);
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

    private boolean isValidToken(UserToken token, RequestContext context) {
        return userService.isValidToken(token, context);
    }
}
