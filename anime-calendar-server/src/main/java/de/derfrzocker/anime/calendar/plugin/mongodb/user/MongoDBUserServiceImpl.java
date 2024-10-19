package de.derfrzocker.anime.calendar.plugin.mongodb.user;

import de.derfrzocker.anime.calendar.server.core.api.anime.AnimeService;
import de.derfrzocker.anime.calendar.server.core.api.user.UserService;
import de.derfrzocker.anime.calendar.server.model.core.anime.AnimeId;
import de.derfrzocker.anime.calendar.server.model.core.user.UserId;
import de.derfrzocker.anime.calendar.server.model.domain.user.User;
import de.derfrzocker.anime.calendar.utils.StringGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@ApplicationScoped
public class MongoDBUserServiceImpl implements UserService {

    private final SecureRandom secureRandom = new SecureRandom();
    private final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    @Inject
    UserDODao userDAO;
    @Inject
    AnimeService animeService;

    @Override
    public User getUser(UserId userId) {
        UserDO userDO = userDAO.findById(userId);

        if (userDO == null) {
            throw new BadRequestException("User not found");
        }

        return new User(userDO.userId, null, null, null);
    }

    @Override
    public User createUser() {
        UserDO userDO = new UserDO();

        userDO.userId = StringGenerator.generateUserId();
        // userDO.animeIDs = new ArrayList<>();

        userDAO.persist(userDO);

        return new User(userDO.userId, null, null, null);
    }

    @Override
    public User updateUser(UserId userId, List<AnimeId> animeIds) {
        User user = getUser(userId);

        for (AnimeId id : animeIds) {
            if (!animeService.isAnime(id)) {
                throw new BadRequestException("Anime ID " + id + " does not exist");
            }
        }

        UserDO userDO = new UserDO();
        userDO.userId = user.userId();
        // userDO.animeIDs = new ArrayList<>(animeIds);
        userDAO.persistOrUpdate(userDO);

        return new User(userDO.userId, null, null, null);
    }

    private String generateKey() {
        byte[] randomBytes = new byte[33];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
