package de.derfrzocker.anime.calendar.server.core.api.permission;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.permission.UserPermission;
import java.util.Optional;

public interface UserPermissionDao {

    Optional<UserPermission> getById(UserId id, RequestContext context);

    void create(UserPermission userPermission, RequestContext context);

    void update(UserPermission userPermission, RequestContext context);

    void delete(UserPermission userPermission, RequestContext context);
}
