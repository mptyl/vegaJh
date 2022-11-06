package it.tylconsulting.vega.util.extjshelpers.filters;


public class TylDateFilter extends TylFilter {
    private final String value;

    public TylDateFilter(String field, String value, String rawComparison) {
        super(field, rawComparison);
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return "DateFilter [value=" + this.value + ", getField()=" + this.getField() + ", getRawComparison()=" + this.getRawComparison() + ", getComparison()=" + "]";
    }
}
