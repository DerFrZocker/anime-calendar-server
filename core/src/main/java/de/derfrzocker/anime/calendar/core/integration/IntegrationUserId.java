package de.derfrzocker.anime.calendar.core.integration;

import java.util.Objects;

public record IntegrationUserId(String raw) {

    public IntegrationUserId {
        Objects.requireNonNull(raw, "Raw value should not be null.");
    }
}
