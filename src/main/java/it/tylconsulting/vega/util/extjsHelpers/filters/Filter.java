package it.tylconsulting.vega.util.extjsHelpers.filters;

import org.springframework.core.convert.ConversionService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Filter {
    private final String field;
    private final String rawComparison;
    private final Comparison comparison;

    public Filter(String field, String rawComparison, Comparison comparison) {
        this.field = field;
        this.rawComparison = rawComparison;
        this.comparison = comparison;
    }

    public String getField() {
        return this.field;
    }

    public String getRawComparison() {
        return this.rawComparison;
    }

    public Comparison getComparison() {
        return this.comparison;
    }

    public String getOperator() {
        return this.rawComparison;
    }

    public static Filter createFilter(Map<String, Object> jsonData, ConversionService conversionService) {
        String type = (String) jsonData.get("type");
        Object source = jsonData.get("value");
        String rawComparison = extractRawComparison(jsonData);
        Comparison comparisonFromJson = Comparison.fromString(rawComparison);
        String property = (String) jsonData.get("property");
        if (property == null) {
            property = (String) jsonData.get("field");
        }

        if (type == null) {
            if (property != null) {
                if (source instanceof Number) {
                    return new NumericFilter(property, (Number) source, rawComparison, comparisonFromJson);
                } else if (source instanceof Boolean) {
                    return new BooleanFilter(property, (Boolean) source, rawComparison, comparisonFromJson);
                } else {
                    return (Filter) (source instanceof List ? new ListFilter(property, (List) source, rawComparison, comparisonFromJson) : new StringFilter(property, source != null ? source.toString() : null, rawComparison, comparisonFromJson));
                }
            } else {
                return null;
            }
        } else if (!type.equals("numeric") && !type.equals("int") && !type.equals("float") && !type.equals("number")) {
            if (type.equals("string")) {
                return new StringFilter(property, (String) source, rawComparison, comparisonFromJson);
            } else if (type.equals("date")) {
                return new DateFilter(property, (String) source, rawComparison, comparisonFromJson);
            } else if (!type.equals("list") && !type.equals("combo")) {
                return type.equals("boolean") ? new BooleanFilter(property, (Boolean) source, rawComparison, comparisonFromJson) : null;
            } else if (source instanceof String) {
                String[] values = ((String) source).split(",");
                return new ListFilter(property, Arrays.asList(values), rawComparison, comparisonFromJson);
            } else {
                return new ListFilter(property, (List) source, rawComparison, comparisonFromJson);
            }
        } else {
            Number value = (Number) conversionService.convert(source, Number.class);
            return new NumericFilter(property, value, rawComparison, comparisonFromJson);
        }
    }

    private static String extractRawComparison(Map<String, Object> jsonData) {
        String comparison = (String) jsonData.get("comparison");
        return comparison != null ? comparison : (String) jsonData.get("operator");
    }
}
