package de.derfrzocker.anime.calendar.server.integration.impl.notify.service;

import static de.derfrzocker.anime.calendar.server.integration.notify.exception.StreamingNotificationExceptions.alreadyCreated;
import static de.derfrzocker.anime.calendar.server.integration.notify.exception.StreamingNotificationExceptions.inconsistentNotFound;
import static de.derfrzocker.anime.calendar.server.integration.notify.exception.StreamingNotificationExceptions.notFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotification;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationCreateData;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationUpdateData;
import de.derfrzocker.anime.calendar.server.integration.notify.dao.StreamingNotificationDao;
import de.derfrzocker.anime.calendar.server.integration.notify.service.StreamingNotificationService;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class StreamingNotificationServiceImpl implements StreamingNotificationService {

    @Inject
    StreamingNotificationDao dao;
    @Inject
    StreamingNotificationEventPublisher eventPublisher;

    @Override
    public Stream<StreamingNotification> getAll(RequestContext context) {
        return this.dao.getAll(context);
    }

    @Override
    public Optional<StreamingNotification> getById(NotificationId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public StreamingNotification createWithData(NotificationId id,
                                                StreamingNotificationCreateData createData,
                                                RequestContext context) {
        Optional<StreamingNotification> optional = getById(id, context);
        if (optional.isPresent()) {
            throw alreadyCreated(id).get();
        }

        StreamingNotification action = StreamingNotification.from(id, createData, context);

        this.eventPublisher.firePreCreate(action, createData, context);
        this.dao.create(action, context);
        this.eventPublisher.firePostCreate(action, createData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public StreamingNotification updateWithData(NotificationId id,
                                                StreamingNotificationUpdateData updateData,
                                                RequestContext context) {
        StreamingNotification current = getById(id, context).orElseThrow(notFound(id));
        StreamingNotification updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdate(current, updated, updateData, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdate(current, updated, updateData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public void deleteById(NotificationId id, RequestContext context) {
        StreamingNotification action = getById(id, context).orElseThrow(notFound(id));

        this.eventPublisher.firePreDelete(action, context);
        this.dao.delete(action, context);
        this.eventPublisher.firePostDelete(action, context);
    }
}
