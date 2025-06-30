package de.derfrzocker.anime.calendar.server.integration.syoboi.data;

import jakarta.xml.bind.annotation.XmlElement;

public class ProgItemTDO {

    @XmlElement(name = "TID")
    public String tid;

    @XmlElement(name = "StTime")
    public String stTime;

    @XmlElement(name = "StOffset")
    public String stOffset;

    @XmlElement(name = "EdTime")
    public String edTime;

    @XmlElement(name = "Count")
    public String count;

    @XmlElement(name = "ChID")
    public String chID;
}
