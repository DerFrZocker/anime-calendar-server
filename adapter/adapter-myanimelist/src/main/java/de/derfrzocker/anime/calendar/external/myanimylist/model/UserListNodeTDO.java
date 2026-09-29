package de.derfrzocker.anime.calendar.external.myanimylist.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public record UserListNodeTDO(@JsonProperty("id") String id) {

    public UserListNodeTDO {
        Objects.requireNonNull(id);
    }
}
