package de.derfrzocker.anime.calendar.notify.jda.service;

import de.derfrzocker.anime.calendar.notify.NotifyActionId;
import de.derfrzocker.anime.calendar.notify.in.ForTriggeringNotifyAction;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class JdaEventService {

    private final ForTriggeringNotifyAction triggerService;

    @Inject
    JdaEventService(ForTriggeringNotifyAction triggerService) {
        this.triggerService = triggerService;
    }

    public void onButtonInteraction(ButtonInteractionEvent event) {
        event.getInteraction().deferEdit().queue();

        String customId = event.getButton().getCustomId();
        if (customId == null) {
            Log.errorf("Got button click with no custom id.");
            return;
        }

        // TODO 2026-11-01: Add request context
        NotifyActionId actionId = NotifyActionId.of(UUID.fromString(customId));
        triggerService.trigger(actionId, null);
    }

    public void onModalInteraction(ModalInteractionEvent event) {
        event.getInteraction().deferEdit().queue();

        // TODO 2026-11-01: Add request context
        NotifyActionId actionId = NotifyActionId.of(UUID.fromString(event.getModalId()));
        triggerService.trigger(
                actionId,
                key -> Optional.ofNullable(event.getValue(key)).map(ModalMapping::getAsString),
                null);
    }
}
