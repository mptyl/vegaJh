package it.tylconsulting.vega.util.extjshelpers.filters;


public class TylBooleanFilter extends TylFilter {
    private final Boolean value;

    public TylBooleanFilter(String field, Boolean value, String rawComparison) {
        super(field, rawComparison);
        this.value = value;
    }

    public Boolean getValue() {
        return this.value;
    }

    public String toString() {
        return "BooleanFilter [value=" + this.value + ", getField()=" + this.getField() + ", getRawComparison()=" + this.getRawComparison() + ", getComparison()=" +  "]";
    }
}
