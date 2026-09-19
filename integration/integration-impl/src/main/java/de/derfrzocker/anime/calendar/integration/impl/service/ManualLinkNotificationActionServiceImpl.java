package de.derfrzocker.anime.calendar.integration.impl.service;

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

import static de.derfrzocker.anime.calendar.integration.exception.ManualLinkNotificationActionExceptions.alreadyCreated;
import static de.derfrzocker.anime.calendar.integration.exception.ManualLinkNotificationActionExceptions.notFound;

@Dependent
public class ManualLinkNotificationActionServiceImpl implements ManualLinkNotificationActionService {

    @Inject
    ManualLinkNotificationActionDao dao;

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

        this.dao.create(action, context);

        return action;
    }

    @Override
    public ManualLinkNotificationAction updateWithData(NotificationActionId id,
                                                       ManualLinkNotificationActionUpdateData updateData,
                                                       RequestContext context) {
        ManualLinkNotificationAction current = getById(id, context).orElseThrow(notFound(id));
        ManualLinkNotificationAction updated = current.updateWithData(updateData, context);

        this.dao.update(updated, context);

        return updated;
    }
}
