package de.derfrzocker.anime.calendar.server.anime.mongodb.dao;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.anime.api.NewAnimeNotification;
import de.derfrzocker.anime.calendar.server.anime.dao.NewAnimeNotificationDao;
import de.derfrzocker.anime.calendar.server.anime.mongodb.mapper.NewAnimeNotificationDataMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class NewAnimeNotificationMongoDBDaoImpl implements NewAnimeNotificationDao {

    @Inject
    NewAnimeNotificationMongoDBRepository repository;

    @Override
    public Optional<NewAnimeNotification> getById(NotificationId id, RequestContext context) {
        return this.repository.findByIdOptional(id).map(NewAnimeNotificationDataMapper::toDomain);
    }

    @Override
    public void create(NewAnimeNotification notification, RequestContext context) {
        this.repository.persist(NewAnimeNotificationDataMapper.toData(notification));
    }
}
