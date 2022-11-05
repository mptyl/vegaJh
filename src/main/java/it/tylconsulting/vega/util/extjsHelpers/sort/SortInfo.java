package it.tylconsulting.vega.util.extjsHelpers.sort;

import ch.ralscha.extdirectspring.bean.SortDirection;

import java.util.Map;

public class SortInfo {
    private final String property;
    private final SortDirection direction;

    public SortInfo(String property, SortDirection direction) {
        this.property = property;
        this.direction = direction;
    }

    public String getProperty() {
        return this.property;
    }

    public SortDirection getDirection() {
        return this.direction;
    }

    public static SortInfo create(Map<String, Object> jsonData) {
        String property = (String)jsonData.get("property");
        String direction = (String)jsonData.get("direction");
        return new SortInfo(property, SortDirection.fromString(direction));
    }

    public String toString() {
        return "SortInfo [property=" + this.property + ", direction=" + this.direction + "]";
    }
}
