package it.tylconsulting.vega.extjs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum Comparison {
    LESS_THAN(new String[]{"lt", "<"}),
    LESS_THAN_OR_EQUAL(new String[]{"lte", "<="}),
    GREATER_THAN(new String[]{"gt", ">"}),
    GREATER_THAN_OR_EQUAL(new String[]{"gte", ">="}),
    EQUAL(new String[]{"eq", "="}),
    NOT_EQUAL(new String[]{"ne", "!="}),
    LIKE(new String[]{"like"}),
    IN(new String[]{"in"});

    private final Set<String> externalValues = new HashSet();

    private Comparison(String... values) {
        Collections.addAll(this.externalValues, values);
    }

    public boolean is(String externalValue) {
        return this.externalValues.contains(externalValue);
    }

    public static Comparison fromString(String externalValue) {
        if (externalValue != null) {
            String externalValueLowerCase = externalValue.toLowerCase();
            Comparison[] var2 = values();
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                Comparison comparison = var2[var4];
                if (comparison.is(externalValueLowerCase)) {
                    return comparison;
                }
            }
        }

        return null;
    }
}
