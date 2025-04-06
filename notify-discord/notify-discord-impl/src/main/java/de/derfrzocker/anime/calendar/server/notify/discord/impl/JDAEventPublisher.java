package de.derfrzocker.anime.calendar.server.notify.discord.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@ApplicationScoped
public class JDAEventPublisher {

    @Inject
    Event<MessageReceivedEvent> messageReceivedEvent;
    @Inject
    Event<ButtonInteractionEvent> buttonInteractionEvent;
    @Inject
    Event<ModalInteractionEvent> modalInteractionEvent;

    public void fire(MessageReceivedEvent event) {
        this.messageReceivedEvent.fire(event);
    }

    public void fire(ButtonInteractionEvent event) {
        this.buttonInteractionEvent.fire(event);
    }

    public void fire(ModalInteractionEvent event) {
        this.modalInteractionEvent.fire(event);
    }
}
