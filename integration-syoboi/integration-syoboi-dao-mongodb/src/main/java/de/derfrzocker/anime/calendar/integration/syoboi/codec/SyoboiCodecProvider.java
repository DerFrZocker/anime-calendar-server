package de.derfrzocker.anime.calendar.integration.syoboi.codec;

import de.derfrzocker.anime.calendar.mongodb.codec.AbstractCodecProvider;
import de.derfrzocker.anime.calendar.integration.syoboi.api.ChannelId;
import de.derfrzocker.anime.calendar.integration.syoboi.api.TID;

public class SyoboiCodecProvider extends AbstractCodecProvider {

    {
        putString(ChannelId.class, ChannelId::raw, ChannelId::new);
        putString(TID.class, TID::raw, TID::new);
    }
}
