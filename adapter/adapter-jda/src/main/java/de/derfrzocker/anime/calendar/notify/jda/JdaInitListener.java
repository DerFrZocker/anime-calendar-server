package de.derfrzocker.anime.calendar.notify.jda;

import de.derfrzocker.anime.calendar.notify.jda.config.JdaConfig;
import de.derfrzocker.anime.calendar.notify.jda.service.JdaEventService;
import io.quarkus.logging.Log;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import java.util.Objects;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jspecify.annotations.NonNull;

@ApplicationScoped
class JdaInitListener {

    private final JdaEventService eventService;
    private final JdaConfig config;
    private Uni<JDA> jda;

    @Inject
    JdaInitListener(JdaEventService eventService, JdaConfig config) {
        this.eventService = eventService;
        this.config = config;
    }

    void onStartup(@Observes StartupEvent event) {
        if (jda != null) {
            Log.errorf("JDA already initialized.");
            return;
        }

        jda = Uni.createFrom().item(() -> {
            JDA jda = JDABuilder.createLight(config.token()).addEventListeners(new ListenerAdapter() {
                @Override
                public void onButtonInteraction(@NonNull ButtonInteractionEvent event) {
                    if (Objects.equals(config.channelId(), event.getChannel().getId())) {
                        eventService.onButtonInteraction(event);
                    }
                }

                @Override
                public void onModalInteraction(@NonNull ModalInteractionEvent event) {
                    if (Objects.equals(config.channelId(), event.getChannel().getId())) {
                        eventService.onModalInteraction(event);
                    }
                }
            }).build();

            try {
                return jda.awaitReady();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Waiting for JDA to be ready was interrupted.", e);
            }
        }).memoize().indefinitely();
    }

    void onShutdown(@Observes ShutdownEvent event) throws InterruptedException {
        if (this.jda == null) {
            Log.errorf("JDA not initialized.");
            return;
        }

        jda.await().indefinitely().shutdown();
        jda.await().indefinitely().awaitShutdown();
    }

    @Produces
    @ApplicationScoped
    public Uni<JDA> jda() {
        if (jda == null) {
            throw new IllegalStateException("JDA not initialized.");
        }

        return jda.memoize().indefinitely();
    }
}
