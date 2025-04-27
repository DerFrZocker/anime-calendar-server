package de.derfrzocker.anime.calendar.server.episode.api;

public record StreamType(String raw) {

    public static final String ORG_RAW = "org";
    public static final StreamType ORG = new StreamType(ORG_RAW);
    public static final String SUB_RAW = "sub";
    public static final StreamType SUB = new StreamType(SUB_RAW);
    public static final String DUB_RAW = "dub";
    public static final StreamType DUB = new StreamType(DUB_RAW);
}
