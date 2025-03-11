package de.derfrzocker.anime.calendar.server.integration.syoboi.codec;

import de.derfrzocker.anime.calendar.mongodb.codec.AbstractCodecProvider;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.Channel;
import de.derfrzocker.anime.calendar.server.integration.syoboi.api.TID;

public class SyoboiCodecProvider extends AbstractCodecProvider {

    {
        putString(Channel.class, Channel::raw, Channel::new);
        putString(TID.class, TID::raw, TID::new);
    }
}
