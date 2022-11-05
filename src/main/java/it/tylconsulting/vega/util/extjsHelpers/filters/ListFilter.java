package it.tylconsulting.vega.util.extjsHelpers.filters;

import java.util.List;

public class ListFilter<T> extends Filter {
    private final List<T> value;

    public ListFilter(String field, List<T> value, String rawComparison, Comparison comparison) {
        super(field, rawComparison, comparison);
        this.value = value;
    }

    public List<T> getValue() {
        return this.value;
    }

    public String toString() {
        return "ListFilter [value=" + this.value + ", getField()=" + this.getField() + ", getRawComparison()=" + this.getRawComparison() + ", getComparison()=" + this.getComparison() + "]";
    }
}
