package de.derfrzocker.anime.calendar.server.layer.exception;

import static de.derfrzocker.anime.calendar.core.util.WrapperUtil.unwrapSafe;
import de.derfrzocker.anime.calendar.core.exception.InconsistentDataException;
import de.derfrzocker.anime.calendar.core.exception.ResourceNotFoundException;
import de.derfrzocker.anime.calendar.core.layer.LayerKey;
import java.util.function.Supplier;

public final class LayerTransformerExceptions {

    private static final String RESOURCE_NAME = "LayerTransformer";

    public static Supplier<ResourceNotFoundException> notFound(LayerKey key) {
        return ResourceNotFoundException.with(unwrapSafe(key, LayerKey::raw), RESOURCE_NAME);
    }

    public static Supplier<InconsistentDataException> inconsistentNotFound(LayerKey key) {
        return InconsistentDataException.with(unwrapSafe(key, LayerKey::raw), RESOURCE_NAME);
    }
}
