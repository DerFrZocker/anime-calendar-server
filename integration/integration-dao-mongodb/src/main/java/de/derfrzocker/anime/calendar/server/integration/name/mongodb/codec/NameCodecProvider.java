package de.derfrzocker.anime.calendar.server.integration.name.mongodb.codec;

import de.derfrzocker.anime.calendar.mongodb.codec.AbstractCodecProvider;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameLanguage;
import de.derfrzocker.anime.calendar.server.integration.name.api.NameType;

public class NameCodecProvider extends AbstractCodecProvider {

    {
        putString(NameType.class, NameType::raw, NameType::new);
        putString(NameLanguage.class, NameLanguage::raw, NameLanguage::new);
    }
}
