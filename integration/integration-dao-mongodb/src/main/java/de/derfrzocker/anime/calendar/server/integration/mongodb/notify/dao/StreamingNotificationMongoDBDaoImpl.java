package de.derfrzocker.anime.calendar.server.integration.mongodb.notify.dao;

import static de.derfrzocker.anime.calendar.server.integration.mongodb.notify.mapper.StreamingNotificationDataMapper.toData;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.integration.mongodb.notify.mapper.StreamingNotificationDataMapper;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotification;
import de.derfrzocker.anime.calendar.server.integration.notify.dao.StreamingNotificationDao;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class StreamingNotificationMongoDBDaoImpl implements StreamingNotificationDao {

    @Inject
    StreamingNotificationMongoDBRepository repository;

    @Override
    public Stream<StreamingNotification> getAll(RequestContext context) {
        return this.repository.findAll().stream().map(StreamingNotificationDataMapper::toDomain);
    }

    @Override
    public Optional<StreamingNotification> getById(NotificationId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(StreamingNotificationDataMapper::toDomain);
    }

    @Override
    public void create(StreamingNotification action, RequestContext context) {
        this.repository.persist(toData(action));
    }

    @Override
    public void update(StreamingNotification action, RequestContext context) {
        this.repository.update(toData(action));
    }

    @Override
    public void delete(StreamingNotification action, RequestContext context) {
        this.repository.deleteById(action.id());
    }
}
