package de.derfrzocker.anime.calendar.server.integration.syoboi.exception;

import static de.derfrzocker.anime.calendar.core.util.WrapperUtil.unwrapSafe;
import de.derfrzocker.anime.calendar.core.exception.InconsistentDataException;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;
import java.util.function.Supplier;

public final class TIDDataExceptions {

    private static final String RESOURCE_NAME = "TIDData";

    public static Supplier<ResourceNotFoundException> notFound(TID id) {
        return ResourceNotFoundException.with(unwrapSafe(id, TID::raw), RESOURCE_NAME);
    }

    public static Supplier<InconsistentDataException> inconsistentNotFound(TID id) {
        return InconsistentDataException.with(unwrapSafe(id, TID::raw), RESOURCE_NAME);
    }
}
