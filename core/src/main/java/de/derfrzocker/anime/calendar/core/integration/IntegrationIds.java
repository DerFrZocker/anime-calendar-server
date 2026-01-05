package de.derfrzocker.anime.calendar.core.integration;

public final class IntegrationIds {

    public static final String ANIDB_RAW = "anidb";
    public static final IntegrationId ANIDB = IntegrationId.of(ANIDB_RAW);
    public static final String CRUNCHYROLL_RAW = "crunchyroll";
    public static final IntegrationId CRUNCHYROLL = IntegrationId.of(CRUNCHYROLL_RAW);
    public static final String MY_ANIME_LIST_RAW = "myanimelist";
    public static final IntegrationId MY_ANIME_LIST = IntegrationId.of(MY_ANIME_LIST_RAW);
    public static final String SYOBOI_RAW = "syoboi";
    public static final IntegrationId SYOBOI = IntegrationId.of(SYOBOI_RAW);

    private IntegrationIds() {
    }
}
