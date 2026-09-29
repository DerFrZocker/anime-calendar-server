package de.derfrzocker.anime.calendar.external.myanimylist.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public record UserListDataTDO(@JsonProperty("node") UserListNodeTDO node) {

    public UserListDataTDO {
        Objects.requireNonNull(node);
    }
}
