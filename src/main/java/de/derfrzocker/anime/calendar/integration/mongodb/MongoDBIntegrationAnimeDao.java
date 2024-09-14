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

package de.derfrzocker.anime.calendar.integration.mongodb;

import de.derfrzocker.anime.calendar.api.anime.AnimeId;
import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeDao;
import de.derfrzocker.anime.calendar.api.integration.IntegrationAnimeId;
import de.derfrzocker.anime.calendar.api.integration.IntegrationId;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
class MongoDBIntegrationAnimeDao implements IntegrationAnimeDao {

    @Inject
    MongoDBIntegrationAnimeRepository repository;

    @Override
    public Set<AnimeId> getAnimeIds(IntegrationId integrationId, Set<IntegrationAnimeId> integrationAnimeIds) {
        Map<IntegrationAnimeId, AnimeId> integrationAnimeIdDOS = new HashMap<>();
        repository.getAll(integrationId).forEach(integrationAnimeId -> integrationAnimeIdDOS.put(integrationAnimeId.integrationAnimeId, integrationAnimeId.animeId));
        return integrationAnimeIds.stream().map(integrationAnimeIdDOS::get).filter(Objects::nonNull).collect(Collectors.toSet());
    }
}
