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

package de.derfrzocker.anime.calendar.integration.myanimelist;

import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.api.integration.IntegrationUserDao;
import de.derfrzocker.anime.calendar.api.integration.IntegrationUserId;
import de.derfrzocker.anime.calendar.integration.Integrations;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Named(Integrations.MY_ANIME_LIST)
class IntegrationUserDaoImpl implements IntegrationUserDao {

    @RestClient
    IntegrationUserRestClient integrationUserRestClient;

    @Override
    public Set<IntegrationAnimeId> getUserIds(IntegrationUserId user) {
        IntegrationUserListResponse watching = integrationUserRestClient.getAnimeList(user.id(), Status.watching);
        IntegrationUserListResponse planToWatch = integrationUserRestClient.getAnimeList(user.id(), Status.plan_to_watch);

        Set<IntegrationAnimeId> userIds = new HashSet<>();
        userIds.addAll(parseResponse(watching));
        userIds.addAll(parseResponse(planToWatch));

        return userIds;
    }

    private Collection<IntegrationAnimeId> parseResponse(IntegrationUserListResponse watching) {
        List<IntegrationAnimeId> userIds = new ArrayList<>();

        for (IntegrationUserListResponse.Data data : watching.data()) {
            userIds.add(new IntegrationAnimeId(String.valueOf(data.node().id())));
        }

        return userIds;
    }
}
