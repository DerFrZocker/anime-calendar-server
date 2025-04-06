package de.derfrzocker.anime.calendar.server.integration.mongodb.dao;

import static de.derfrzocker.anime.calendar.server.integration.mongodb.mapper.ManualLinkNotificationActionDataMapper.toData;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.api.ManualLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.dao.ManualLinkNotificationActionDao;
import de.derfrzocker.anime.calendar.server.integration.mongodb.mapper.ManualLinkNotificationActionDataMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class ManualLinkNotificationActionMongoDBDaoImpl implements ManualLinkNotificationActionDao {

    @Inject
    ManualLinkNotificationActionMongoDBRepository repository;

    @Override
    public Stream<ManualLinkNotificationAction> getAll(RequestContext context) {
        return this.repository.findAll().stream().map(ManualLinkNotificationActionDataMapper::toDomain);
    }

    @Override
    public Optional<ManualLinkNotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(ManualLinkNotificationActionDataMapper::toDomain);
    }

    @Override
    public void create(ManualLinkNotificationAction action, RequestContext context) {
        this.repository.persist(toData(action));
    }

    @Override
    public void update(ManualLinkNotificationAction action, RequestContext context) {
        this.repository.update(toData(action));
    }

    @Override
    public void delete(ManualLinkNotificationAction action, RequestContext context) {
        this.repository.deleteById(action.id());
    }
}
