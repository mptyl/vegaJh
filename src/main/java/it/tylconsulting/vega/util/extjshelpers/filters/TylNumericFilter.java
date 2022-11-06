package it.tylconsulting.vega.util.extjshelpers.filters;


public class TylNumericFilter extends TylFilter {
    private final Number value;

    public TylNumericFilter(String field, Number value, String rawComparison) {
        super(field, rawComparison);
        this.value = value;
    }

    public Number getValue() {
        return this.value;
    }

    public String toString() {
        return "NumericFilter [value=" + this.value + ", getField()=" + this.getField() + ", getRawComparison()=" + this.getRawComparison() + ", getComparison()=" + "]";
    }
}
