/*
 * MIT License
 *
 * Copyright (c) 2022 Marvin (DerFrZocker)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.derfrzocker.anime.calendar.plugin.mongodb.user;

import de.derfrzocker.anime.calendar.api.anime.AnimeId;
import de.derfrzocker.anime.calendar.api.anime.AnimeService;
import de.derfrzocker.anime.calendar.api.user.User;
import de.derfrzocker.anime.calendar.api.user.UserId;
import de.derfrzocker.anime.calendar.api.user.UserService;
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
