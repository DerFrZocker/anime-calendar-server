package de.derfrzocker.anime.calendar.server.core.api.user;

import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import java.util.List;

public interface UserService {

    User getUser(UserId userId);

    User createUser();

    User updateUser(UserId userId, List<AnimeId> animeIds);
}
