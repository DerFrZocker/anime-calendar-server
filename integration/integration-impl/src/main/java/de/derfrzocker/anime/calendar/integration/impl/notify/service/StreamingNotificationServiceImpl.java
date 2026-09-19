package de.derfrzocker.anime.calendar.integration.impl.notify.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationId;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotification;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotificationCreateData;
import de.derfrzocker.anime.calendar.integration.notify.dao.StreamingNotificationDao;
import de.derfrzocker.anime.calendar.integration.notify.service.StreamingNotificationService;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.Optional;

import static de.derfrzocker.anime.calendar.integration.notify.exception.StreamingNotificationExceptions.alreadyCreated;
import static de.derfrzocker.anime.calendar.integration.notify.exception.StreamingNotificationExceptions.inconsistentNotFound;

@Dependent
public class StreamingNotificationServiceImpl implements StreamingNotificationService {

    @Inject
    StreamingNotificationDao dao;

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

        this.dao.create(action, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }
}
