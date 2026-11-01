package de.derfrzocker.anime.calendar.notify;

import de.derfrzocker.anime.calendar.metadata.SimpleMetadataHolder;
import java.time.Instant;

public interface Notify<T extends NotifyData> extends SimpleMetadataHolder {

    NotifyId id();

    Instant validUntil();

    NotifyType type();

    NotifyDataType dataType();

    T data();
}
