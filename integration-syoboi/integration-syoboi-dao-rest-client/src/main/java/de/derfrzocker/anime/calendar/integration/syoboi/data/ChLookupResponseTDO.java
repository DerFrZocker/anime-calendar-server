package de.derfrzocker.anime.calendar.integration.syoboi.data;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ChLookupResponse")
public class ChLookupResponseTDO {

    @XmlElement(name = "ChItems")
    public ChItemsTDO chItems;
}
