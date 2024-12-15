package de.derfrzocker.anime.calendar.server.mongodb.dao.permission;

import de.derfrzocker.anime.calendar.server.core.api.permission.PredicateParserService;
import de.derfrzocker.anime.calendar.server.core.api.permission.UserPermissionDao;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import de.derfrzocker.anime.calendar.server.model.domain.permission.UserPermission;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.data.UserPermissionData;
import de.derfrzocker.anime.calendar.server.mongodb.mapper.domain.UserPermissionDomain;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@RequestScoped
public class UserPermissionMongoDBDaoImpl implements UserPermissionDao {

    @Inject
    UserPermissionMongoDBRepository repository;
    @Inject
    PredicateParserService parserService;

    @Override
    public Optional<UserPermission> getById(UserId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(data -> UserPermissionData.toDomain(data, this.parserService));
    }

    @Override
    public void create(UserPermission userPermission, RequestContext context) {
        this.repository.persist(UserPermissionDomain.toData(userPermission, this.parserService));
    }

    @Override
    public void update(UserPermission userPermission, RequestContext context) {
        this.repository.update(UserPermissionDomain.toData(userPermission, this.parserService));
    }

    @Override
    public void delete(UserPermission userPermission, RequestContext context) {
        this.repository.deleteById(userPermission.id());
    }
}
