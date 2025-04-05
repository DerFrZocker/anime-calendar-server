package de.derfrzocker.anime.calendar.server.integration.syoboi.impl.service;

import static de.derfrzocker.anime.calendar.server.integration.syoboi.exception.IgnoreTIDDataNotificationActionExceptions.inconsistentNotFound;
import static de.derfrzocker.anime.calendar.server.integration.syoboi.exception.IgnoreTIDDataNotificationActionExceptions.notFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.IgnoreTIDDataNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.integration.syoboi.dao.IgnoreTIDDataNotificationActionDao;
import de.derfrzocker.anime.calendar.server.integration.syoboi.service.IgnoreTIDDataNotificationActionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class IgnoreTIDDataNotificationActionServiceImpl implements IgnoreTIDDataNotificationActionService {

    @Inject
    IgnoreTIDDataNotificationActionDao dao;
    @Inject
    IgnoreTIDDataNotificationActionEventPublisher eventPublisher;

    @Override
    public Stream<IgnoreTIDDataNotificationAction> getAll(RequestContext context) {
        return this.dao.getAll(context);
    }

    @Override
    public Optional<IgnoreTIDDataNotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public IgnoreTIDDataNotificationAction createWithData(NotificationActionId id,
                                                          IgnoreTIDDataNotificationActionCreateData createData,
                                                          RequestContext context) {
        IgnoreTIDDataNotificationAction action = IgnoreTIDDataNotificationAction.from(id, createData, context);

        this.eventPublisher.firePreCreate(action, createData, context);
        this.dao.create(action, context);
        this.eventPublisher.firePostCreate(action, createData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public IgnoreTIDDataNotificationAction updateWithData(NotificationActionId id,
                                                          IgnoreTIDDataNotificationActionUpdateData updateData,
                                                          RequestContext context) {
        IgnoreTIDDataNotificationAction current = getById(id, context).orElseThrow(notFound(id));
        IgnoreTIDDataNotificationAction updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdate(current, updated, updateData, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdate(current, updated, updateData, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }

    @Override
    public void deleteById(NotificationActionId id, RequestContext context) {
        IgnoreTIDDataNotificationAction action = getById(id, context).orElseThrow(notFound(id));

        this.eventPublisher.firePreDelete(action, context);
        this.dao.delete(action, context);
        this.eventPublisher.firePostDelete(action, context);
    }
}
