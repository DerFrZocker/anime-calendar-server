package de.derfrzocker.anime.calendar.integration.syoboi.impl.service;

import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.integration.syoboi.api.IgnoreTIDDataNotificationAction;
import de.derfrzocker.anime.calendar.integration.syoboi.api.IgnoreTIDDataNotificationActionCreateData;
import de.derfrzocker.anime.calendar.integration.syoboi.dao.IgnoreTIDDataNotificationActionDao;
import de.derfrzocker.anime.calendar.integration.syoboi.service.IgnoreTIDDataNotificationActionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

import static de.derfrzocker.anime.calendar.integration.syoboi.exception.IgnoreTIDDataNotificationActionExceptions.inconsistentNotFound;

@ApplicationScoped
public class IgnoreTIDDataNotificationActionServiceImpl implements IgnoreTIDDataNotificationActionService {

    @Inject
    IgnoreTIDDataNotificationActionDao dao;

    @Override
    public Optional<IgnoreTIDDataNotificationAction> getById(NotificationActionId id, RequestContext context) {
        return this.dao.getById(id, context);
    }

    @Override
    public IgnoreTIDDataNotificationAction createWithData(NotificationActionId id,
                                                          IgnoreTIDDataNotificationActionCreateData createData,
                                                          RequestContext context) {
        IgnoreTIDDataNotificationAction action = IgnoreTIDDataNotificationAction.from(id, createData, context);

        this.dao.create(action, context);

        return getById(id, context).orElseThrow(inconsistentNotFound(id));
    }
}
