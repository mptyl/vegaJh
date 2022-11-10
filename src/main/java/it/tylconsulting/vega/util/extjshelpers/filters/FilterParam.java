package it.tylconsulting.vega.util.extjshelpers.filters;

public class FilterParam {
    private String operator;
    private Object value;
    private String property;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "FilterParam{" +
            "operator='" + operator + '\'' +
            ", value=" + value +
            ", property='" + property + '\'' +
            '}';
    }
}
