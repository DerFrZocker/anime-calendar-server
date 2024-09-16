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

package de.derfrzocker.anime.calendar.impl.layer.filter;

import de.derfrzocker.anime.calendar.api.AnimeOptions;
import de.derfrzocker.anime.calendar.api.EpisodeBuilder;
import de.derfrzocker.anime.calendar.api.anime.Anime;
import de.derfrzocker.anime.calendar.impl.layer.config.BoundFilterConfig;
import de.derfrzocker.anime.calendar.impl.layer.parser.BoundConfigLayerParser;

public final class BoundLayerFilter extends AbstractLayerFilter<BoundFilterConfig> {

    public static final BoundLayerFilter INSTANCE = new BoundLayerFilter();

    private BoundLayerFilter() {
        super("bound-filter", BoundConfigLayerParser.INSTANCE);
    }

    @Override
    public boolean shouldSkip(Anime anime, AnimeOptions animeOptions, BoundFilterConfig layerConfig, EpisodeBuilder episodeBuilder) {
        if (episodeBuilder.episodeIndex() < layerConfig.minInclusive()) {
            return true;
        }

        return layerConfig.maxInclusive() != -1 && episodeBuilder.episodeIndex() > layerConfig.maxInclusive();
    }
}
