package de.derfrzocker.anime.calendar.server.integration.syoboi.api;

import org.jspecify.annotations.Nullable;

public record TrackingChannelNotificationCreateData(
        TID tid, String title, @Nullable ChannelId currentChannelId, @Nullable String currentChannelName) {

}
