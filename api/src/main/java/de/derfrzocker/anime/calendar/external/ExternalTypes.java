package de.derfrzocker.anime.calendar.external;

public final class ExternalTypes {

    public static final String MYANIMELIST_RAW = "myanimelist";
    public static final ExternalType MYANIMELIST = new ExternalType(MYANIMELIST_RAW);
    public static final String ANIDB_RAW = "anidb";
    public static final ExternalType ANIDB = new ExternalType(ANIDB_RAW);
    public static final String SYOBOI_RAW = "syoboi";
    public static final ExternalType SYOBOI = new ExternalType(SYOBOI_RAW);

    private ExternalTypes() {
    }
}
