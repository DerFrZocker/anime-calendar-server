package de.derfrzocker.anime.calendar.server.integration.syoboi.dao;

import static org.junit.jupiter.api.Assertions.*;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ProvidedChannel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.dao.support.WireMockExtensions;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.util.Optional;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(WireMockExtensions.class)
class ChannelProviderRestDaoImplTest {

    @Inject
    ChannelProviderDao dao;

    @Test
    void testReturnCorrect() {
        ProvidedChannel expected = new ProvidedChannel(new ChannelId("7"), "テレビ東京");
        Optional<ProvidedChannel> channel = this.dao.provideById(new ChannelId("7"), null);
        assertTrue(channel.isPresent(), "Provided channel should exist");
        assertEquals(expected, channel.get(), "Provided channel should be equals.");
    }
}
