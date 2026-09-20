package de.derfrzocker.anime.calendar.external;

import de.derfrzocker.anime.calendar.anime.StreamType;

public final class ExternalTypes {

    public static final String MYANIMELIST_RAW = "myanimelist";
    public static final StreamType MYANIMELIST = new StreamType(MYANIMELIST_RAW);
    public static final String ANIDB_RAW = "anidb";
    public static final StreamType ANIDB = new StreamType(ANIDB_RAW);
    public static final String SYOBOI_RAW = "syoboi";
    public static final StreamType SYOBOI = new StreamType(SYOBOI_RAW);

    private ExternalTypes() {
    }
}
