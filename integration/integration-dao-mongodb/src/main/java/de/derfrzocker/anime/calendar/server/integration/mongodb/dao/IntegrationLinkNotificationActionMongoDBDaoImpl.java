package de.derfrzocker.anime.calendar.server.integration.mongodb.dao;

import static de.derfrzocker.anime.calendar.server.integration.mongodb.mapper.IntegrationLinkNotificationActionDataMapper.toData;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.dao.IntegrationLinkNotificationActionDao;
import de.derfrzocker.anime.calendar.server.integration.mongodb.mapper.IntegrationLinkNotificationActionDataMapper;
import de.derfrzocker.anime.calendar.server.model.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.model.domain.RequestContext;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class IntegrationLinkNotificationActionMongoDBDaoImpl implements IntegrationLinkNotificationActionDao {

    @Inject
    IntegrationLinkNotificationActionMongoDBRepository repository;

    @Override
    public Stream<IntegrationLinkNotificationAction> getAll(RequestContext context) {
        return this.repository.findAll().stream().map(IntegrationLinkNotificationActionDataMapper::toDomain);
    }

    @Override
    public Optional<IntegrationLinkNotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(IntegrationLinkNotificationActionDataMapper::toDomain);
    }

    @Override
    public void create(IntegrationLinkNotificationAction notificationAction, RequestContext context) {
        this.repository.persist(toData(notificationAction));
    }

    @Override
    public void update(IntegrationLinkNotificationAction notificationAction, RequestContext context) {
        this.repository.update(toData(notificationAction));
    }

    @Override
    public void delete(IntegrationLinkNotificationAction notificationAction, RequestContext context) {
        this.repository.deleteById(notificationAction.id());
    }
}
