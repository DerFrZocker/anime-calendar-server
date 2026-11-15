package de.derfrzocker.anime.calendar.notify.config;

import de.derfrzocker.anime.calendar.external.ExternalAnimeId;
import de.derfrzocker.anime.calendar.external.ExternalType;
import de.derfrzocker.anime.calendar.notify.NotifyData;
import de.derfrzocker.anime.calendar.notify.NotifyDataType;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public record NewAnimeFromExternalTypeWithCustomNameActionConfig(
        ExternalType externalType, ExternalAnimeId externalAnimeId, @Nullable String _name) implements NotifyData {

    public static final String DATA_TYE_RAW = "new_anime_from_external_type_with_custom_name_action";
    public static final NotifyDataType DATA_TYE = NotifyDataType.of(DATA_TYE_RAW);

    public NewAnimeFromExternalTypeWithCustomNameActionConfig {
        Objects.requireNonNull(externalType);
        Objects.requireNonNull(externalAnimeId);
    }

    public Optional<String> name() {
        return Optional.ofNullable(_name);
    }
}
