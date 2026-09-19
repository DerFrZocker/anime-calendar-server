package de.derfrzocker.anime.calendar.integration.impl.notify.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotificationAction;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotificationActionCreateData;
import de.derfrzocker.anime.calendar.integration.notify.api.StreamingNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.integration.notify.dao.StreamingNotificationActionDao;
import de.derfrzocker.anime.calendar.integration.notify.service.StreamingNotificationActionService;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.Optional;

import static de.derfrzocker.anime.calendar.integration.notify.exception.StreamingNotificationActionExceptions.alreadyCreated;
import static de.derfrzocker.anime.calendar.integration.notify.exception.StreamingNotificationActionExceptions.inconsistentNotFound;
import static de.derfrzocker.anime.calendar.integration.notify.exception.StreamingNotificationActionExceptions.notFound;

@Dependent
public class StreamingNotificationActionServiceImpl implements StreamingNotificationActionService {

    @Inject
    StreamingNotificationActionDao dao;

    @Override
    public Optional<StreamingNotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public StreamingNotificationAction createWithData(NotificationActionId id,
                                                      StreamingNotificationActionCreateData createData,
                                                      RequestContext context) {
        Optional<StreamingNotificationAction> optional = getById(id, context);
        if (optional.isPresent()) {
            throw alreadyCreated(id).get();
        }

        StreamingNotificationAction action = StreamingNotificationAction.from(id, createData, context);

        this.dao.create(action, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public StreamingNotificationAction updateWithData(NotificationActionId id,
                                                      StreamingNotificationActionUpdateData updateData,
                                                      RequestContext context) {
        StreamingNotificationAction current = getById(id, context).orElseThrow(notFound(id));
        StreamingNotificationAction updated = current.updateWithData(updateData, context);

        this.dao.update(updated, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }
}
