package it.tylconsulting.vega.util.extjshelpers.sort;

public enum SortDirection {
    ASC("ASC"),
    DESC("DESC");

    private final String name;

    private SortDirection(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static SortDirection fromString(String name) {
        if (ASC.getName().equalsIgnoreCase(name)) {
            return ASC;
        } else {
            return DESC.getName().equalsIgnoreCase(name) ? DESC : null;
        }
    }
}
