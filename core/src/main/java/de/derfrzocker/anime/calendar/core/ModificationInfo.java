package de.derfrzocker.anime.calendar.core;

import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;

public interface ModificationInfo {

    Instant createdAt();

    UserId createdBy();

    Instant updatedAt();

    UserId updatedBy();
}
