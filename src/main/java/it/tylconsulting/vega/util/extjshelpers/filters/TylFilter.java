package it.tylconsulting.vega.util.extjshelpers.filters;

import org.springframework.core.convert.ConversionService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class TylFilter {
    private final String field;
    private final String rawComparison;

    public TylFilter(String field, String rawComparison) {
        this.field = field;
        this.rawComparison = rawComparison;
    }

    public String getField() {
        return this.field;
    }

    public String getRawComparison() {
        return this.rawComparison;
    }


    public String getOperator() {
        return this.rawComparison;
    }

    public static TylFilter createFilter(Map<String, Object> jsonData, ConversionService conversionService) {
        String type = (String) jsonData.get("type");
        Object source = jsonData.get("value");
        String rawComparison = extractRawComparison(jsonData);
        String property = (String) jsonData.get("property");
        if (property == null) {
            property = (String) jsonData.get("field");
        }

        if (type == null) {
            if (property != null) {
                if (source instanceof Number) {
                    return new TylNumericFilter(property, (Number) source, rawComparison);
                } else if (source instanceof Boolean) {
                    return new TylBooleanFilter(property, (Boolean) source, rawComparison);
                } else {
                    return (TylFilter) (source instanceof List ? new TylListFilter(property, (List) source, rawComparison) : new TylStringFilter(property, source != null ? source.toString() : null, rawComparison));
                }
            } else {
                return null;
            }
        } else if (!type.equals("numeric") && !type.equals("int") && !type.equals("float") && !type.equals("number")) {
            if (type.equals("string")) {
                return new TylStringFilter(property, (String) source, rawComparison);
            } else if (type.equals("date")) {
                return new TylDateFilter(property, (String) source, rawComparison);
            } else if (!type.equals("list") && !type.equals("combo")) {
                return type.equals("boolean") ? new TylBooleanFilter(property, (Boolean) source, rawComparison) : null;
            } else if (source instanceof String) {
                String[] values = ((String) source).split(",");
                return new TylListFilter(property, Arrays.asList(values), rawComparison);
            } else {
                return new TylListFilter(property, (List) source, rawComparison);
            }
        } else {
            Number value = (Number) conversionService.convert(source, Number.class);
            return new TylNumericFilter(property, value, rawComparison);
        }
    }

    private static String extractRawComparison(Map<String, Object> jsonData) {
        String comparison = (String) jsonData.get("comparison");
        return comparison != null ? comparison : (String) jsonData.get("operator");
    }
}
