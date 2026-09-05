package de.derfrzocker.anime.calendar.integration.syoboi.data;

import jakarta.xml.bind.annotation.XmlElement;
import java.util.List;

public class ProgItemsTDO {

    @XmlElement(name = "ProgItem")
    public List<ProgItemTDO> progItems;
}
