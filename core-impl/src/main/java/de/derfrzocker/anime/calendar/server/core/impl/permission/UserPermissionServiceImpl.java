package de.derfrzocker.anime.calendar.server.core.impl.permission;

import de.derfrzocker.anime.calendar.server.core.api.permission.UserPermissionDao;
import de.derfrzocker.anime.calendar.server.core.api.permission.UserPermissionService;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.model.domain.permission.UserPermission;
import de.derfrzocker.anime.calendar.server.model.domain.permission.UserPermissionCreateData;
import de.derfrzocker.anime.calendar.server.model.domain.permission.UserPermissionUpdateData;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@RequestScoped
public class UserPermissionServiceImpl implements UserPermissionService {

    @Inject
    UserPermissionDao dao;

    @Override
    public Optional<UserPermission> getById(UserId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public UserPermission createWithData(UserId id, UserPermissionCreateData createData, RequestContext context) {
        UserPermission userPermission = UserPermission.from(id, createData, context);

        // TODO 2024-12-13: Call event
        this.dao.create(userPermission, context);

        return userPermission;
    }

    @Override
    public UserPermission updateWithData(UserId id, UserPermissionUpdateData updateData, RequestContext context) {
        UserPermission current = getById(id, context).orElseThrow(ResourceNotFoundException.withPermission(id));
        UserPermission updated = current.updateWithData(updateData, context);

        // TODO 2024-12-13: Call event
        this.dao.update(updated, context);

        return updated;
    }
}
