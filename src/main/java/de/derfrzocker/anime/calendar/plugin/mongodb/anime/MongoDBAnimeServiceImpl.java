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

package de.derfrzocker.anime.calendar.plugin.mongodb.anime;

import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.api.anime.AnimeId;
import de.derfrzocker.anime.calendar.api.anime.AnimeService;
import de.derfrzocker.anime.calendar.api.layer.Layer;
import de.derfrzocker.anime.calendar.api.layer.LayerDataHolder;
import de.derfrzocker.anime.calendar.api.layer.LayerKey;
import de.derfrzocker.anime.calendar.api.layer.LayerService;
import de.derfrzocker.anime.calendar.utils.StringGenerator;
import de.derfrzocker.anime.calendar.web.request.anime.AnimePostRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.Nullable;

@ApplicationScoped
public class MongoDBAnimeServiceImpl implements AnimeService {

    @Inject
    AnimeDODao animeDODao;
    @Inject
    LayerService layerService;

    @Override
    public @Nullable Anime getAnime(AnimeId id) {
        validateId(id);
        AnimeDO animeDO = animeDODao.findById(id);

        if (animeDO == null) {
            return null;
        }

        List<LayerDataHolder<?>> layerDataHolders = new ArrayList<>();
        for (Map<String, Object> map : animeDO.episodeLayers) {
            LayerKey layerKey = new LayerKey((String) map.get("layer_key"));
            Layer<?> layer = layerService.getLayer(layerKey);

            if (layer == null) {
                throw new IllegalArgumentException("Layer " + layerKey + " not found");
            }

            layerDataHolders.add(layer.createLayerDataHolder(map));
        }

        return new Anime(animeDO.animeId, animeDO.animeTitle, animeDO.episodeCount, layerDataHolders);
    }

    @Override
    public boolean isAnime(AnimeId id) {
        validateId(id);
        return animeDODao.count("_id", id) >= 1;
    }

    @Override
    public Anime createAnime(AnimePostRequest animePostRequest) {
        AnimeDO animeDO = new AnimeDO();

        List<LayerDataHolder<?>> layerDataHolders = new ArrayList<>();
        for (Map<String, Object> map : animePostRequest.episodeLayers()) {
            LayerKey layerKey = new LayerKey((String) map.get("layer_key"));
            Layer<?> layer = layerService.getLayer(layerKey);

            if (layer == null) {
                throw new IllegalArgumentException("Layer " + layerKey + " not found");
            }

            layerDataHolders.add(layer.createLayerDataHolder(map));
        }

        List<Map<String, Object>> layerDocuments = new ArrayList<>();
        for (LayerDataHolder<?> layerDataHolder : layerDataHolders) {
            Map<String, Object> map = new LinkedHashMap<>(layerDataHolder.encode());
            map.put("layer_key", layerDataHolder.layer().getLayerKey().key());
            layerDocuments.add(map);
        }

        animeDO.animeTitle = animePostRequest.animeTitle();
        animeDO.episodeCount = animePostRequest.episodeCount();
        animeDO.episodeLayers = layerDocuments;

        do {
            animeDO.animeId = StringGenerator.generateAnimeId();
        } while (animeDODao.count("_id", animeDO.animeId) > 0);

        animeDODao.persist(animeDO);

        return new Anime(animeDO.animeId, animeDO.animeTitle, animeDO.episodeCount, layerDataHolders);
    }

    private String generateId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        char[] result = new char[10];

        for (int i = 0; i < result.length; i++) {
            result[i] = chars.charAt((ThreadLocalRandom.current().nextInt(chars.length())));
        }

        return new String(result);
    }

    private void validateId(AnimeId animeId) {
        if (animeId == null || animeId.id().isEmpty()) {
            throw new BadRequestException("Anime ID must not be empty");
        }

        for (char c : animeId.id().toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                continue;
            }

            if (c >= '0' && c <= '9') {
                continue;
            }

            throw new BadRequestException("Invalid anime ID: " + animeId);
        }
    }
}