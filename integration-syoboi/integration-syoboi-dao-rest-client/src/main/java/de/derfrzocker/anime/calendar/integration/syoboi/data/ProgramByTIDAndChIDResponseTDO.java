package de.derfrzocker.anime.calendar.integration.syoboi.data;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ProgLookupResponse")
public class ProgramByTIDAndChIDResponseTDO {

    @XmlElement(name = "ProgItems")
    public ProgItemsTDO progItems;
}
