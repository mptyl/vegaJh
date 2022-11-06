package it.tylconsulting.vega.util.extjshelpers.filters;

import java.util.List;

public class TylListFilter<T> extends TylFilter {
    private final List<T> value;

    public TylListFilter(String field, List<T> value, String rawComparison) {
        super(field, rawComparison);
        this.value = value;
    }

    public List<T> getValue() {
        return this.value;
    }

    public String toString() {
        return "ListFilter [value=" + this.value + ", getField()=" + this.getField() + ", getRawComparison()=" + this.getRawComparison() + ", getComparison()="  + "]";
    }
}
