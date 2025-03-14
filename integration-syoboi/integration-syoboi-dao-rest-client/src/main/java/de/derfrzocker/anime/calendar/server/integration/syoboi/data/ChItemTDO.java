package de.derfrzocker.anime.calendar.server.integration.syoboi.data;

import jakarta.xml.bind.annotation.XmlElement;

public class ChItemTDO {

    @XmlElement(name = "ChID")
    public String chId;

    @XmlElement(name = "ChName")
    public String chName;
}
