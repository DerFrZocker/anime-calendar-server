package de.derfrzocker.anime.calendar.server.layer2.api;

import java.util.List;

public record LayerStepConfig(List<LayerConfig> filterConfigs, LayerConfig transformConfig) {

}
