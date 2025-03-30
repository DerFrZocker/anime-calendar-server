package de.derfrzocker.anime.calendar.server.notify.discord.impl;

import de.derfrzocker.anime.calendar.server.notify.discord.impl.config.DiscordConfig;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.jetbrains.annotations.NotNull;

@ApplicationScoped
public class JDAInitListener {

    private static final Logger LOG = Logger.getLogger(JDAInitListener.class);

    @ConfigProperty(name = "discord.bot.token")
    String discordToken;
    @Inject
    JDAEventPublisher eventPublisher;
    @Inject
    DiscordConfig config;

    private CompletableFuture<JDA> jda;

    public void onStartup(@Observes StartupEvent event) {
        if (this.jda != null) {
            LOG.error("JDA already initialized.");
            return;
        }

        this.jda = CompletableFuture.supplyAsync(() -> {
            JDA jda = JDABuilder.createLight(this.discordToken).addEventListeners(new ListenerAdapter() {
                @Override
                public void onMessageReceived(@NotNull MessageReceivedEvent event) {
                    if (Objects.equals(config.getChannelId(), event.getChannel().getId())) {
                        eventPublisher.fire(event);
                    }
                }

                @Override
                public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
                    if (Objects.equals(config.getChannelId(), event.getChannel().getId())) {
                        eventPublisher.fire(event);
                    }
                }
            }).build();
            try {
                jda.awaitReady();
            } catch (InterruptedException e) {
                throw new RuntimeException("Waiting for JDA to be ready was interrupted.", e);
            }
            return jda;
        });
    }

    public void onShutdown(@Observes ShutdownEvent event) throws ExecutionException, InterruptedException {
        if (this.jda == null) {
            LOG.error("JDA not initialized.");
            return;
        }

        this.jda.get().shutdown();
        this.jda.get().awaitShutdown();
    }

    @Produces
    @ApplicationScoped
    public JDA getJDA() throws ExecutionException, InterruptedException {
        if (this.jda == null) {
            throw new IllegalStateException("JDA not initialized.");
        }

        return this.jda.get();
    }
}
