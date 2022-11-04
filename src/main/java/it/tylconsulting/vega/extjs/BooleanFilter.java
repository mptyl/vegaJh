package it.tylconsulting.vega.extjs;


public class BooleanFilter extends Filter {
    private final Boolean value;

    public BooleanFilter(String field, Boolean value, String rawComparison, Comparison comparison) {
        super(field, rawComparison, comparison);
        this.value = value;
    }

    public Boolean getValue() {
        return this.value;
    }

    public String toString() {
        return "BooleanFilter [value=" + this.value + ", getField()=" + this.getField() + ", getRawComparison()=" + this.getRawComparison() + ", getComparison()=" + this.getComparison() + "]";
    }
}
