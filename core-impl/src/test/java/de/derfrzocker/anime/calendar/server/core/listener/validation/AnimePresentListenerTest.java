package de.derfrzocker.anime.calendar.server.core.listener.validation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import de.derfrzocker.anime.calendar.core.RequestContext;
import de.derfrzocker.anime.calendar.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.core.exception.InvalidValueException;
import de.derfrzocker.anime.calendar.core.user.UserId;
import de.derfrzocker.anime.calendar.server.anime.api.Anime;
import de.derfrzocker.anime.calendar.server.anime.service.AnimeService;
import de.derfrzocker.anime.calendar.server.integration.api.AnimeIntegrationLink;
import de.derfrzocker.anime.calendar.server.integration.event.PreAnimeIntegrationLinkCreateEvent;
import de.derfrzocker.anime.calendar.server.model.domain.event.calendar.PreCalendarAnimeLinkCreateEvent;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnimePresentListenerTest {

    private static final RequestContext REQUEST_CONTEXT = new RequestContext(UserId.of("UWSXEDCRFV"), Instant.now());
    private static final AnimeId ANIME_ID = AnimeId.of("AWSXEDCRFV");
    private static final Anime ANIME = createWithId(ANIME_ID);

    private static Anime createWithId(AnimeId id) {
        return new Anime(id, null, null, null, null, null, 0, null);
    }

    private AnimeService service;
    private AnimePresentListener listener;

    @BeforeEach
    public void setup() {
        this.service = mock();
        this.listener = new AnimePresentListener();

        this.listener.service = service;
    }

    // <editor-fold desc="#onPreCalendarAnimeLinkCreate(PreCalendarAnimeLinkCreateEvent)" defaultstate="collapsed">
    @Test
    void testOnPreCalendarAnimeLinkCreate_NotPresent() {
        PreCalendarAnimeLinkCreateEvent event = new PreCalendarAnimeLinkCreateEvent(null,
                                                                                    ANIME_ID,
                                                                                    null,
                                                                                    null,
                                                                                    REQUEST_CONTEXT);

        assertThrows(InvalidValueException.class, () -> this.listener.onPreCalendarAnimeLinkCreate(event));

        verify(this.service).getById(ANIME_ID, REQUEST_CONTEXT);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void testOnPreCalendarAnimeLinkCreate_Present() {
        when(this.service.getById(ANIME_ID, REQUEST_CONTEXT)).thenReturn(Optional.of(ANIME));

        PreCalendarAnimeLinkCreateEvent event = new PreCalendarAnimeLinkCreateEvent(null,
                                                                                    ANIME_ID,
                                                                                    null,
                                                                                    null,
                                                                                    REQUEST_CONTEXT);

        assertDoesNotThrow(() -> this.listener.onPreCalendarAnimeLinkCreate(event));

        verify(this.service).getById(ANIME_ID, REQUEST_CONTEXT);
        verifyNoMoreInteractions(this.service);
    }
    // </editor-fold>

    // <editor-fold desc="#onPreAnimeIntegrationLinkCreate(PreAnimeIntegrationLinkCreateEvent)" defaultstate="collapsed">
    @Test
    void testOnPreAnimeIntegrationLinkCreate_NotPresent() {
        PreAnimeIntegrationLinkCreateEvent event = new PreAnimeIntegrationLinkCreateEvent(new AnimeIntegrationLink(
                ANIME_ID,
                null,
                null,
                null,
                null,
                null,
                null), null, REQUEST_CONTEXT);

        assertThrows(InvalidValueException.class, () -> this.listener.onPreAnimeIntegrationLinkCreate(event));

        verify(this.service).getById(ANIME_ID, REQUEST_CONTEXT);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    void testOnPreAnimeIntegrationLinkCreate_Present() {
        when(this.service.getById(ANIME_ID, REQUEST_CONTEXT)).thenReturn(Optional.of(ANIME));

        PreAnimeIntegrationLinkCreateEvent event = new PreAnimeIntegrationLinkCreateEvent(new AnimeIntegrationLink(
                ANIME_ID,
                null,
                null,
                null,
                null,
                null,
                null), null, REQUEST_CONTEXT);

        assertDoesNotThrow(() -> this.listener.onPreAnimeIntegrationLinkCreate(event));

        verify(this.service).getById(ANIME_ID, REQUEST_CONTEXT);
        verifyNoMoreInteractions(this.service);
    }
    // </editor-fold>
}
