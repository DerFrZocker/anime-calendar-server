package de.derfrzocker.anime.calendar.integration.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.api.IntegrationLinkNotificationAction;
import de.derfrzocker.anime.calendar.integration.api.IntegrationLinkNotificationActionCreateData;
import de.derfrzocker.anime.calendar.integration.dao.IntegrationLinkNotificationActionDao;
import de.derfrzocker.anime.calendar.integration.service.IntegrationLinkNotificationActionService;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.Optional;

import static de.derfrzocker.anime.calendar.integration.exception.IntegrationLinkNotificationActionExceptions.alreadyCreated;

@Dependent
public class IntegrationLinkNotificationActionServiceImpl implements IntegrationLinkNotificationActionService {

    @Inject
    IntegrationLinkNotificationActionDao dao;

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

        this.dao.create(action, context);

        return action;
    }
}
