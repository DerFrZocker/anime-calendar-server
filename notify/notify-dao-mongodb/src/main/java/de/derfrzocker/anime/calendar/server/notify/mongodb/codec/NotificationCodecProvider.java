package de.derfrzocker.anime.calendar.server.notify.mongodb.codec;

import de.derfrzocker.anime.calendar.mongodb.codec.AbstractCodecProvider;
import de.derfrzocker.anime.calendar.core.notify.NotificationActionType;
import de.derfrzocker.anime.calendar.core.notify.NotificationType;

public class NotificationCodecProvider extends AbstractCodecProvider {

    {
        putString(NotificationType.class, NotificationType::raw, NotificationType::new);
        putString(NotificationActionType.class, NotificationActionType::raw, NotificationActionType::new);
    }
}
