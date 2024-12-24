package de.derfrzocker.anime.calendar.server.impl.notify.discord;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.jboss.logging.Logger;

@ApplicationScoped
public class JavacordInitListener {

    private static final Logger LOG = Logger.getLogger(JavacordInitListener.class);

    @ConfigProperty(name = "discord.bot.token")
    String discordToken;
    @Inject
    JavacordEventPublisher eventPublisher;

    private CompletableFuture<DiscordApi> discordApi;

    public void onStartup(@Observes StartupEvent event) {
        if (this.discordApi != null) {
            LOG.error("Javacord already initialized.");
            return;
        }

        this.discordApi = new DiscordApiBuilder().setToken(this.discordToken).login().thenApply(api -> {
            api.addMessageCreateListener(messageCreateEvent -> {
                this.eventPublisher.fire(messageCreateEvent);
            });
            api.addButtonClickListener(buttonClickEvent -> {
                this.eventPublisher.fire(buttonClickEvent);
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
