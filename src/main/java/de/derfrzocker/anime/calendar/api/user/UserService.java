package de.derfrzocker.anime.calendar.api.user;

import de.derfrzocker.anime.calendar.api.anime.AnimeId;
import java.util.List;

public interface UserService {

    User getUser(UserId userId);

    User createUser();

    User updateUser(UserId userId, List<AnimeId> animeIds);
}
