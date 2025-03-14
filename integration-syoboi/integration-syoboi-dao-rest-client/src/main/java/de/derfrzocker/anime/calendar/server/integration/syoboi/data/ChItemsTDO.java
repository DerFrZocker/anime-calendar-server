package de.derfrzocker.anime.calendar.server.integration.syoboi.data;

import jakarta.xml.bind.annotation.XmlElement;

public class ChItemsTDO {

    @XmlElement(name = "ChItem")
    public ChItemTDO chItem;
}
