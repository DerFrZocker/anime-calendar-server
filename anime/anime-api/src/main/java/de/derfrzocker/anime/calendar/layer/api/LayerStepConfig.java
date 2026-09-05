package de.derfrzocker.anime.calendar.layer.api;

import java.util.List;

public record LayerStepConfig(List<LayerConfig> filterConfigs, LayerConfig transformConfig) {

}
