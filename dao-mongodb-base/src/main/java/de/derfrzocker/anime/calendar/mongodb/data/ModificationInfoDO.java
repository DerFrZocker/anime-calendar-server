package de.derfrzocker.anime.calendar.mongodb.data;

import de.derfrzocker.anime.calendar.core.ModificationInfo;
import de.derfrzocker.anime.calendar.core.user.UserId;
import java.time.Instant;

public class ModificationInfoDO {

    public Instant createdAt;
    public UserId createdBy;
    public Instant updatedAt;
    public UserId updatedBy;

    public void apply(ModificationInfo info) {
        this.createdAt = info.createdAt();
        this.createdBy = info.createdBy();
        this.updatedAt = info.updatedAt();
        this.updatedBy = info.updatedBy();
    }
}
