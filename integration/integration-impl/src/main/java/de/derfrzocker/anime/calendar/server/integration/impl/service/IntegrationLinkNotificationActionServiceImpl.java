package de.derfrzocker.anime.calendar.server.integration.impl.service;

import static de.derfrzocker.anime.calendar.server.integration.exception.IntegrationLinkNotificationActionExceptions.alreadyCreated;
import static de.derfrzocker.anime.calendar.server.integration.exception.IntegrationLinkNotificationActionExceptions.notFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationActionCreateData;
import de.derfrzocker.anime.calendar.server.integration.api.IntegrationLinkNotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.integration.dao.IntegrationLinkNotificationActionDao;
import de.derfrzocker.anime.calendar.server.integration.service.IntegrationLinkNotificationActionService;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

@Dependent
public class IntegrationLinkNotificationActionServiceImpl implements IntegrationLinkNotificationActionService {

    @Inject
    IntegrationLinkNotificationActionDao dao;
    @Inject
    IntegrationLinkNotificationActionEventPublisher eventPublisher;

    @Override
    public Stream<IntegrationLinkNotificationAction> getAll(RequestContext context) {
        return this.dao.getAll(context);
    }

    @Override
    public Optional<IntegrationLinkNotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public IntegrationLinkNotificationAction createWithData(NotificationActionId id,
                                                            IntegrationLinkNotificationActionCreateData createData,
                                                            RequestContext context) {
        Optional<IntegrationLinkNotificationAction> optional = getById(id, context);
        if (optional.isPresent()) {
            throw alreadyCreated(id).get();
        }

        IntegrationLinkNotificationAction action = IntegrationLinkNotificationAction.from(id, createData, context);

        this.eventPublisher.firePreCreate(action, createData, context);
        this.dao.create(action, context);
        this.eventPublisher.firePostCreate(action, createData, context);

        return action;
    }

    @Override
    public IntegrationLinkNotificationAction updateWithData(NotificationActionId id,
                                                            IntegrationLinkNotificationActionUpdateData updateData,
                                                            RequestContext context) {
        IntegrationLinkNotificationAction current = getById(id, context).orElseThrow(notFound(id));
        IntegrationLinkNotificationAction updated = current.updateWithData(updateData, context);

        this.eventPublisher.firePreUpdate(current, updated, updateData, context);
        this.dao.update(updated, context);
        this.eventPublisher.firePostUpdate(current, updated, updateData, context);

        return updated;
    }

    @Override
    public void deleteById(NotificationActionId id, RequestContext context) {
        IntegrationLinkNotificationAction action = getById(id, context).orElseThrow(notFound(id));

        this.eventPublisher.firePreDelete(action, context);
        this.dao.delete(action, context);
        this.eventPublisher.firePostDelete(action, context);
    }
}
