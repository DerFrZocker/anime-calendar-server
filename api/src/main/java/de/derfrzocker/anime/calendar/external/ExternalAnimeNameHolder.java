package de.derfrzocker.anime.calendar.external;

import de.derfrzocker.anime.calendar.RequestContext;
import de.derfrzocker.anime.calendar.metadata.SimpleEntityMetadata;
import de.derfrzocker.anime.calendar.metadata.SimpleMetadataHolder;

import java.util.List;

public record ExternalAnimeNameHolder(
        ExternalAnimeNameId id,
        ExternalId externalId,
        ExternalAnimeId externalAnimeId,
        NameType type,
        NameLanguage language,
        List<String> names,
        SimpleEntityMetadata metadata) implements SimpleMetadataHolder {

    public static ExternalAnimeNameHolder from(
            ExternalAnimeNameId id,
            ExternalAnimeNameCreateData createData,
            RequestContext context) {

        return new ExternalAnimeNameHolder(
                id,
                createData.externalId(),
                createData.externalAnimeId(),
                createData.type(),
                createData.language(),
                createData.names(),
                SimpleEntityMetadata.from(context));
    }

    public ExternalAnimeNameHolder update(ExternalAnimeNameUpdateData updateData, RequestContext context) {

        return new ExternalAnimeNameHolder(
                id(),
                externalId(),
                externalAnimeId(),
                type(),
                language(),
                updateData.names().apply(names()),
                metadata().updateWithData(context));
    }
}
