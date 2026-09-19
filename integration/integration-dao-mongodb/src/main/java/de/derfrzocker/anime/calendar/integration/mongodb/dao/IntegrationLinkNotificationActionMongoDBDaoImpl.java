package de.derfrzocker.anime.calendar.integration.mongodb.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.integration.dao.IntegrationLinkNotificationActionDao;
import de.derfrzocker.anime.calendar.integration.mongodb.mapper.IntegrationLinkNotificationActionDataMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.Optional;

import static de.derfrzocker.anime.calendar.integration.mongodb.mapper.IntegrationLinkNotificationActionDataMapper.toData;

@Dependent
public class IntegrationLinkNotificationActionMongoDBDaoImpl implements IntegrationLinkNotificationActionDao {

    @Inject
    IntegrationLinkNotificationActionMongoDBRepository repository;

    @Override
    public Optional<IntegrationLinkNotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(IntegrationLinkNotificationActionDataMapper::toDomain);
    }

    @Override
    public void create(IntegrationLinkNotificationAction notificationAction, RequestContext context) {
        this.repository.persist(toData(notificationAction));
    }
}
