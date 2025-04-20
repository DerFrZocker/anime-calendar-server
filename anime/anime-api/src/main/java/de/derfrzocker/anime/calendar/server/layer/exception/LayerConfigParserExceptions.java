package de.derfrzocker.anime.calendar.server.layer.exception;

import static de.derfrzocker.anime.calendar.core.util.WrapperUtil.unwrapSafe;
import de.derfrzocker.anime.calendar.core.exception.InconsistentDataException;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.core.layer.LayerParserKey;
import java.util.function.Supplier;

public final class LayerConfigParserExceptions {

    private static final String RESOURCE_NAME = "LayerConfigParser";

    public static Supplier<ResourceNotFoundException> notFound(LayerParserKey key) {
        return ResourceNotFoundException.with(unwrapSafe(key, LayerParserKey::raw), RESOURCE_NAME);
    }

    public static Supplier<InconsistentDataException> inconsistentNotFound(LayerParserKey key) {
        return InconsistentDataException.with(unwrapSafe(key, LayerParserKey::raw), RESOURCE_NAME);
    }
}
