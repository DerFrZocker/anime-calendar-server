package de.derfrzocker.anime.calendar.integration.impl.service;

import static de.derfrzocker.anime.calendar.integration.exception.ManualLinkNotificationActionExceptions.alreadyCreated;
import static de.derfrzocker.anime.calendar.integration.exception.ManualLinkNotificationActionExceptions.notFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.api.ManualLinkNotificationAction;
import de.derfrzocker.anime.calendar.integration.api.ManualLinkNotificationActionCreateData;
import de.derfrzocker.anime.calendar.integration.api.ManualLinkNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.integration.dao.ManualLinkNotificationActionDao;
import de.derfrzocker.anime.calendar.integration.service.ManualLinkNotificationActionService;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class ManualLinkNotificationActionServiceImpl implements ManualLinkNotificationActionService {

    @Inject
    ManualLinkNotificationActionDao dao;
    @Inject
    ManualLinkNotificationActionEventPublisher eventPublisher;

    @Override
    public Stream<ManualLinkNotificationAction> getAll(RequestContext context) {
        return this.dao.getAll(context);
    }

    @Override
    public Optional<ManualLinkNotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public ManualLinkNotificationAction createWithData(NotificationActionId id,
                                                       ManualLinkNotificationActionCreateData createData,
                                                       RequestContext context) {
        Optional<ManualLinkNotificationAction> optional = getById(id, context);
        if (optional.isPresent()) {
            throw alreadyCreated(id).get();
        }

        ManualLinkNotificationAction action = ManualLinkNotificationAction.from(id, createData, context);

        this.eventPublisher.firePreCreate(action, createData, context);
        this.dao.create(action, context);
        this.eventPublisher.firePostCreate(action, createData, context);

        return action;
    }

    @Override
    public ManualLinkNotificationAction updateWithData(NotificationActionId id,
                                                       ManualLinkNotificationActionUpdateData updateData,
                                                       RequestContext context) {
        ManualLinkNotificationAction current = getById(id, context).orElseThrow(notFound(id));
        ManualLinkNotificationAction updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdate(current, updated, updateData, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdate(current, updated, updateData, context);

        return updated;
    }

    @Override
    public void deleteById(NotificationActionId id, RequestContext context) {
        ManualLinkNotificationAction action = getById(id, context).orElseThrow(notFound(id));

        this.eventPublisher.firePreDelete(action, context);
        this.dao.delete(action, context);
        this.eventPublisher.firePostDelete(action, context);
    }
}
