package de.derfrzocker.anime.calendar.server.core.api.permission;

import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.permission.UserPermission;
import de.derfrzocker.anime.calendar.server.model.domain.permission.UserPermissionUpdateData;
import de.derfrzocker.anime.calendar.server.model.domain.permission.UserPermissionCreateData;
import java.util.Optional;

public interface UserPermissionService {

    Optional<UserPermission> getById(UserId id, RequestContext context);

    UserPermission createWithData(UserId id, UserPermissionCreateData createData, RequestContext context);

    UserPermission updateWithData(UserId id, UserPermissionUpdateData updateData, RequestContext context);
}
