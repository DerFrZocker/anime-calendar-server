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

package de.derfrzocker.anime.calendar.impl.layer;

import de.derfrzocker.anime.calendar.api.layer.Layer;
import de.derfrzocker.anime.calendar.api.layer.LayerConfig;
import de.derfrzocker.anime.calendar.api.layer.LayerConfigParser;
import de.derfrzocker.anime.calendar.api.layer.LayerKey;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractLayer<T extends LayerConfig, H> implements Layer<T, H> {

    @NotNull
    private final LayerKey layerKey;
    @NotNull
    private final LayerConfigParser<T> layerConfigParser;

    protected AbstractLayer(@NotNull String layerKey, @NotNull LayerConfigParser<T> layerConfigParser) {
        this.layerKey = new LayerKey(layerKey);
        this.layerConfigParser = layerConfigParser;
    }

    @Override
    public @NotNull LayerKey getLayerKey() {
        return layerKey;
    }

    @Override
    public @NotNull LayerConfigParser<T> getLayerConfigParser() {
        return layerConfigParser;
    }
}
