package de.derfrzocker.anime.calendar.integration.mongodb.notify.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.integration.mongodb.notify.mapper.StreamingNotificationDataMapper;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotification;
import de.derfrzocker.anime.calendar.integration.notify.dao.StreamingNotificationDao;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.Optional;

import static de.derfrzocker.anime.calendar.integration.mongodb.notify.mapper.StreamingNotificationDataMapper.toData;

@Dependent
public class StreamingNotificationMongoDBDaoImpl implements StreamingNotificationDao {

    @Inject
    StreamingNotificationMongoDBRepository repository;

    @Override
    public Optional<StreamingNotification> getById(NotificationId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(StreamingNotificationDataMapper::toDomain);
    }

    @Override
    public void create(StreamingNotification action, RequestContext context) {
        this.repository.persist(toData(action));
    }
}
