package it.tylconsulting.vega.util.extjsHelpers.filters;


public class DateFilter extends Filter {
    private final String value;

    public DateFilter(String field, String value, String rawComparison, Comparison comparison) {
        super(field, rawComparison, comparison);
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return "DateFilter [value=" + this.value + ", getField()=" + this.getField() + ", getRawComparison()=" + this.getRawComparison() + ", getComparison()=" + this.getComparison() + "]";
    }
}
