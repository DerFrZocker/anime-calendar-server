package de.derfrzocker.anime.calendar.integration.myanimelist.restclient.data;

public record SimpleRestResponseHolder<T>(int status, T data) {

}
