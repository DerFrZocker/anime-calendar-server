package de.derfrzocker.anime.calendar.server.integration.mongodb.notify.dao;

import static de.derfrzocker.anime.calendar.server.integration.mongodb.notify.mapper.StreamingNotificationActionDataMapper.toData;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.mongodb.notify.mapper.StreamingNotificationActionDataMapper;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.notify.dao.StreamingNotificationActionDao;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class StreamingNotificationActionMongoDBDaoImpl implements StreamingNotificationActionDao {

    @Inject
    StreamingNotificationActionMongoDBRepository repository;

    @Override
    public Stream<StreamingNotificationAction> getAll(RequestContext context) {
        return this.repository.findAll().stream().map(StreamingNotificationActionDataMapper::toDomain);
    }

    @Override
    public Optional<StreamingNotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(StreamingNotificationActionDataMapper::toDomain);
    }

    @Override
    public void create(StreamingNotificationAction action, RequestContext context) {
        this.repository.persist(toData(action));
    }

    @Override
    public void update(StreamingNotificationAction action, RequestContext context) {
        this.repository.update(toData(action));
    }

    @Override
    public void delete(StreamingNotificationAction action, RequestContext context) {
        this.repository.deleteById(action.id());
    }
}
