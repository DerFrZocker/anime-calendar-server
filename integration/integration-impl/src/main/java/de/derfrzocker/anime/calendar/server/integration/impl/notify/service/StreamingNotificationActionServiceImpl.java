package de.derfrzocker.anime.calendar.server.integration.impl.notify.service;

import static de.derfrzocker.anime.calendar.server.integration.notify.exception.StreamingNotificationActionExceptions.alreadyCreated;
import static de.derfrzocker.anime.calendar.server.integration.notify.exception.StreamingNotificationActionExceptions.inconsistentNotFound;
import static de.derfrzocker.anime.calendar.server.integration.notify.exception.StreamingNotificationActionExceptions.notFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.notify.api.StreamingNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.integration.notify.dao.StreamingNotificationActionDao;
import de.derfrzocker.anime.calendar.server.integration.notify.service.StreamingNotificationActionService;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class StreamingNotificationActionServiceImpl implements StreamingNotificationActionService {

    @Inject
    StreamingNotificationActionDao dao;
    @Inject
    StreamingNotificationActionEventPublisher eventPublisher;

    @Override
    public Stream<StreamingNotificationAction> getAll(RequestContext context) {
        return this.dao.getAll(context);
    }

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

        this.eventPublisher.firePreCreate(action, createData, context);
        this.dao.create(action, context);
        this.eventPublisher.firePostCreate(action, createData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public StreamingNotificationAction updateWithData(NotificationActionId id,
                                                      StreamingNotificationActionUpdateData updateData,
                                                      RequestContext context) {
        StreamingNotificationAction current = getById(id, context).orElseThrow(notFound(id));
        StreamingNotificationAction updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdate(current, updated, updateData, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdate(current, updated, updateData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public void deleteById(NotificationActionId id, RequestContext context) {
        StreamingNotificationAction action = getById(id, context).orElseThrow(notFound(id));

        this.eventPublisher.firePreDelete(action, context);
        this.dao.delete(action, context);
        this.eventPublisher.firePostDelete(action, context);
    }
}
