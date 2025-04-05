package de.derfrzocker.anime.calendar.server.anime.mongodb.dao;

import static de.derfrzocker.anime.calendar.server.anime.mongodb.mapper.NewAnimeNotificationActionDataMapper.toData;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotificationAction;
import de.derfrzocker.anime.calendar.server.anime.dao.NewAnimeNotificationActionDao;
import de.derfrzocker.anime.calendar.server.anime.mongodb.mapper.NewAnimeNotificationActionDataMapper;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class NewAnimeNotificationActionMongoDBDaoImpl implements NewAnimeNotificationActionDao {

    @Inject
    NewAnimeNotificationActionMongoDBRepository repository;

    @Override
    public Stream<NewAnimeNotificationAction> getAll(RequestContext context) {
        return this.repository.findAll().stream().map(NewAnimeNotificationActionDataMapper::toDomain);
    }

    @Override
    public Optional<NewAnimeNotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(NewAnimeNotificationActionDataMapper::toDomain);
    }

    @Override
    public void create(NewAnimeNotificationAction action, RequestContext context) {
        this.repository.persist(toData(action));
    }

    @Override
    public void update(NewAnimeNotificationAction action, RequestContext context) {
        this.repository.update(toData(action));
    }

    @Override
    public void delete(NewAnimeNotificationAction action, RequestContext context) {
        this.repository.deleteById(action.id());
    }
}
