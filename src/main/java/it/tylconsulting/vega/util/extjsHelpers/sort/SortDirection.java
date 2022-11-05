package it.tylconsulting.vega.util.extjsHelpers.sort;

public enum SortDirection {
    ASCENDING("ASC"),
    DESCENDING("DESC");

    private final String name;

    private SortDirection(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static SortDirection fromString(String name) {
        if (ASCENDING.getName().equalsIgnoreCase(name)) {
            return ASCENDING;
        } else {
            return DESCENDING.getName().equalsIgnoreCase(name) ? DESCENDING : null;
        }
    }
}
