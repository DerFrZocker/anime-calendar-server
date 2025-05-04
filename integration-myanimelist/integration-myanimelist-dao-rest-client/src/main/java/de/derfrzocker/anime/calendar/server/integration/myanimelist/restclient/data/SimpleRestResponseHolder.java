package de.derfrzocker.anime.calendar.server.integration.myanimelist.restclient.data;

public record SimpleRestResponseHolder<T>(int status, T data) {

}
