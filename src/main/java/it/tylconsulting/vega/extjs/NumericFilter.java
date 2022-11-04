package it.tylconsulting.vega.extjs;


public class NumericFilter extends Filter {
    private final Number value;

    public NumericFilter(String field, Number value, String rawComparison, Comparison comparison) {
        super(field, rawComparison, comparison);
        this.value = value;
    }

    public Number getValue() {
        return this.value;
    }

    public String toString() {
        return "NumericFilter [value=" + this.value + ", getField()=" + this.getField() + ", getRawComparison()=" + this.getRawComparison() + ", getComparison()=" + this.getComparison() + "]";
    }
}
