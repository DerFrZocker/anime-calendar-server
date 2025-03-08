package de.derfrzocker.anime.calendar.server.impl.notify.discord;

import de.derfrzocker.anime.calendar.server.impl.notify.discord.config.DiscordConfig;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.DiscordEntity;
import org.jboss.logging.Logger;

@ApplicationScoped
public class JavacordInitListener {

    private static final Logger LOG = Logger.getLogger(JavacordInitListener.class);

    @ConfigProperty(name = "discord.bot.token")
    String discordToken;
    @Inject
    JavacordEventPublisher eventPublisher;
    @Inject
    DiscordConfig config;

    private CompletableFuture<DiscordApi> discordApi;

    public void onStartup(@Observes StartupEvent event) {
        if (this.discordApi != null) {
            LOG.error("Javacord already initialized.");
            return;
        }

        this.discordApi = new DiscordApiBuilder().setToken(this.discordToken).login().thenApply(api -> {
            api.addMessageCreateListener(messageCreateEvent -> {
                if (Objects.equals(this.config.getChannelId(), messageCreateEvent.getChannel().getIdAsString())) {
                    this.eventPublisher.fire(messageCreateEvent);
                }
            });
            api.addButtonClickListener(buttonClickEvent -> {
                if (buttonClickEvent.getButtonInteraction()
                                    .getChannel()
                                    .map(DiscordEntity::getIdAsString)
                                    .filter(id -> Objects.equals(this.config.getChannelId(), id))
                                    .isPresent()) {
                    this.eventPublisher.fire(buttonClickEvent);
                }
            });

            return api;
        });
    }

    public void onShutdown(@Observes ShutdownEvent event) throws ExecutionException, InterruptedException {
        if (this.discordApi == null) {
            LOG.error("Javacord not initialized.");
            return;
        }

        this.discordApi.get().disconnect().join();
    }

    @Produces
    @ApplicationScoped
    public DiscordApi getDiscordApi() throws ExecutionException, InterruptedException {
        if (this.discordApi == null) {
            throw new IllegalStateException("Discord API not initialized.");
        }

        return this.discordApi.get();
    }
}
