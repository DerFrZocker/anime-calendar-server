package de.derfrzocker.anime.calendar.server;

public record Configuration(String databaseUsername, String databasePassword, String databaseName, String databaseHostAndPort, String proxerApiKey) {
}
