package de.derfrzocker.anime.calendar.server.impl.notify.discord.listener;

import static de.derfrzocker.anime.calendar.server.notify.exception.NotificationExceptions.inconsistentNotFound;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionId;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.util.Change;
import de.derfrzocker.anime.calendar.server.notify.api.Notification;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationAction;
import de.derfrzocker.anime.calendar.server.notify.api.NotificationActionUpdateData;
import de.derfrzocker.anime.calendar.server.notify.event.NotificationActionTriggerEvent;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationActionService;
import de.derfrzocker.anime.calendar.server.notify.service.NotificationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import java.time.Instant;
import java.util.Optional;
import org.javacord.api.event.interaction.ButtonClickEvent;
import org.javacord.api.interaction.ButtonInteraction;

// TODO 2024-12-22: For better security in the future, we should link the discord account to a userid and check for
//  the users permission, this way we can also drag which user exactly made the change.
//  we should also use a random id and store the corresponding action on the server -> do not trust client data
//  this way we can also better invalidated old ids.
//  For now however it is fine as it is.
@ApplicationScoped
public class ButtonClickListener {

    private static final UserId LINKING_USER = new UserId("UDIANILINK");

    @Inject
    NotificationService notificationService;
    @Inject
    NotificationActionService actionService;
    @Inject
    Event<NotificationActionTriggerEvent> notificationActionTriggerEvent;

    public void onButtonClicked(@Observes ButtonClickEvent event) {
        ButtonInteraction interaction = event.getButtonInteraction();
        NotificationActionId customId = new NotificationActionId(interaction.getCustomId());
        RequestContext context = new RequestContext(LINKING_USER, Instant.now());

        Optional<NotificationAction> oAction = this.actionService.getById(customId, context);

        if (oAction.isEmpty()) {
            return;
        }

        interaction.acknowledge();

        NotificationAction action = oAction.get();
        if (action.executedAt() != null) {
            return;
        }

        Notification notification = this.notificationService.getById(action.notificationId(), context)
                                                            .orElseThrow(inconsistentNotFound(action.notificationId()));
        if (Instant.now().isAfter(notification.validUntil())) {
            // TODO 2025-02-25: Should we remove it?
            return;
        }

        NotificationActionUpdateData updateData = new NotificationActionUpdateData(Change.to(context.requestTime()),
                                                                                   Change.to(context.requestUser()));
        action = this.actionService.updateWithData(action.id(), updateData, context);

        this.notificationActionTriggerEvent.fire(new NotificationActionTriggerEvent(notification, action, context));
    }
}
