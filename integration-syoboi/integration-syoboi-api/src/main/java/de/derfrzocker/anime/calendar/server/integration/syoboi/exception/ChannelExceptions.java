package de.derfrzocker.anime.calendar.server.integration.syoboi.exception;

import static de.derfrzocker.anime.calendar.server.model.domain.util.WrapperUtil.unwrapSafe;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.server.model.domain.exception.InconsistentDataException;
import de.derfrzocker.anime.calendar.server.model.domain.exception.ResourceNotFoundException;
import java.util.function.Supplier;

public final class ChannelExceptions {

    private static final String RESOURCE_NAME = "Channel";

    public static Supplier<ResourceNotFoundException> notFound(ChannelId id) {
        return ResourceNotFoundException.with(unwrapSafe(id, ChannelId::raw), RESOURCE_NAME);
    }

    public static Supplier<InconsistentDataException> inconsistentNotFound(ChannelId id) {
        return InconsistentDataException.with(unwrapSafe(id, ChannelId::raw), RESOURCE_NAME);
    }
}
