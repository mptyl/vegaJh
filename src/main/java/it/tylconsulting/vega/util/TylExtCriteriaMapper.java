package it.tylconsulting.vega.util;

import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.NumericFilter;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public class TylExtCriteriaMapper {

    public static Criteria mapCriterias(List<Filter> filters, Criteria criteria) {
        Criteria myCriteria = criteria;
        filters.forEach(filter -> {
            try {
                setFilter(filter, myCriteria);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        });
        return myCriteria;
    }

    private static Criteria setFilter(Filter filter, Criteria criteria) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        Field f = criteria.getClass().getDeclaredField(filter.getField());
        String fieldType = f.getType().getSimpleName();

        String methodName = "set" + TylUtil.capitalize(filter.getField());
        Method meth;

        switch (fieldType) {
            case "StringFilter" -> {
                ch.ralscha.extdirectspring.filter.StringFilter ralschaStringFilter= (ch.ralscha.extdirectspring.filter.StringFilter) filter;
                String filterValue = ralschaStringFilter.getValue();
                StringFilter stringFilter = new StringFilter();
                stringFilter.setContains(filterValue);
                meth = criteria.getClass().getMethod(methodName, StringFilter.class);
                meth.invoke(criteria, stringFilter);
            }
            case "BooleanFilter" -> {
                ch.ralscha.extdirectspring.filter.BooleanFilter ralschaBooleanFilter= (ch.ralscha.extdirectspring.filter.BooleanFilter) filter;
                Boolean filterValue = Boolean.valueOf(ralschaBooleanFilter.getValue());
                BooleanFilter booleanFilter = new BooleanFilter();
                booleanFilter.setEquals(filterValue);
                meth = criteria.getClass().getMethod(methodName, BooleanFilter.class);
                meth.invoke(criteria, booleanFilter);
            }
            case "ShortFilter" -> {
                NumericFilter numFilter=(NumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                ShortFilter criteriaFilter = new ShortFilter();
                mapNumberParameters((NumericFilter) filter, ShortFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "IntegerFilter" -> {
                NumericFilter numFilter=(NumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                IntegerFilter criteriaFilter = new IntegerFilter();
                mapNumberParameters((NumericFilter) filter, IntegerFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "LongFilter" -> {
                NumericFilter numFilter=(NumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                LongFilter criteriaFilter = new LongFilter();
                mapNumberParameters((NumericFilter) filter, LongFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "FloatFilter" -> {
                NumericFilter numFilter=(NumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                FloatFilter criteriaFilter = new FloatFilter();
                mapNumberParameters((NumericFilter) filter, FloatFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "DoubleFilter" -> {
                NumericFilter numFilter=(NumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                DoubleFilter criteriaFilter = new DoubleFilter();
                mapNumberParameters((NumericFilter) filter, DoubleFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "BigDecimalFilter" -> {
                NumericFilter numFilter=(NumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                BigDecimalFilter criteriaFilter = new BigDecimalFilter();
                mapNumberParameters((NumericFilter) filter, BigDecimalFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "LocalDateFilter" -> {
                ch.ralscha.extdirectspring.filter.StringFilter dateFilter=(ch.ralscha.extdirectspring.filter.StringFilter) filter;
                String filterValue = dateFilter.getValue();
                LocalDateFilter criteriaFilter = new LocalDateFilter();
                mapDateParameters(dateFilter, LocalDateFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "ZonedDateFilter" -> {
                ch.ralscha.extdirectspring.filter.StringFilter dateFilter=(ch.ralscha.extdirectspring.filter.StringFilter) filter;
                String filterValue = dateFilter.getValue();
                ZonedDateTimeFilter criteriaFilter = new ZonedDateTimeFilter();
                mapDateParameters(dateFilter, ZonedDateTimeFilter.class, filterValue, criteriaFilter, criteria);
            }
        }
        return criteria;
    }

    private static void mapNumberParameters(NumericFilter filter, Class<?> filterClass, String valueInFilter, RangeFilter<?> criteriaFilter, Criteria criteria) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "set" + TylUtil.capitalize(filter.getField());
        Method meth = criteria.getClass().getMethod(methodName, filterClass);

        switch (criteriaFilter.getClass().getSimpleName()) {
            case "ShortFilter" -> {
                Short value = Short.valueOf(valueInFilter);
                setNumericComparison(filter, criteria, criteriaFilter, value, meth);
            }
            case "IntegerFilter" -> {
                Integer value = Integer.valueOf(valueInFilter);
                setNumericComparison(filter, criteria, criteriaFilter, value, meth);
            }
            case "LongFilter" -> {
                Long value = Long.valueOf(valueInFilter);
                setNumericComparison(filter, criteria, criteriaFilter, value, meth);
            }
            case "FloatFilter" -> {
                Float value = Float.valueOf(valueInFilter);
                setNumericComparison(filter, criteria, criteriaFilter, value, meth);
            }
            case "DoubleFilter" -> {
                Double value = Double.valueOf(valueInFilter);
                setNumericComparison(filter, criteria, criteriaFilter, value, meth);
            }
            case "BigDecimalFilter" -> {
                BigDecimal value = new BigDecimal(valueInFilter);
                setNumericComparison(filter, criteria, criteriaFilter, value, meth);
            }
        }
    }

    private static void mapDateParameters(ch.ralscha.extdirectspring.filter.StringFilter filter, Class<?> filterClass, String valueInFilter, RangeFilter<?> criteriaFilter, Criteria criteria) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "set" + TylUtil.capitalize(filter.getField());
        Method meth = criteria.getClass().getMethod(methodName, filterClass);

        switch (criteriaFilter.getClass().getSimpleName()) {
            case "LocalDateFilter" -> {
                LocalDate value = LocalDate.parse(valueInFilter);
                setLocalDateComparison(filter, criteria, (LocalDateFilter) criteriaFilter, value, meth);
            }
            case "ZonedDateTimeFilter" -> {
                ZonedDateTime value = ZonedDateTime.parse(valueInFilter);
                setZonedDateTimeComparison(filter, criteria, (ZonedDateTimeFilter) criteriaFilter, value, meth);
            }
        }
    }

    private static void setLocalDateComparison(ch.ralscha.extdirectspring.filter.StringFilter filter, Criteria criteria, LocalDateFilter criteriaFilter, LocalDate value, Method meth) throws InvocationTargetException, IllegalAccessException {
        switch (filter.getComparison()) {
            case EQUAL -> criteriaFilter.setEquals(value);
            case GREATER_THAN_OR_EQUAL -> criteriaFilter.setGreaterThanOrEqual(value);
            case GREATER_THAN -> criteriaFilter.setGreaterThan(value);
            case LESS_THAN_OR_EQUAL -> criteriaFilter.setLessThanOrEqual(value);
            case LESS_THAN -> criteriaFilter.setLessThan(value);
        }
        meth.invoke(criteria, criteriaFilter);
    }

    private static void setZonedDateTimeComparison(ch.ralscha.extdirectspring.filter.StringFilter filter, Criteria criteria, ZonedDateTimeFilter criteriaFilter, ZonedDateTime value, Method meth) throws InvocationTargetException, IllegalAccessException {
        switch (filter.getComparison()) {
            case EQUAL -> criteriaFilter.setEquals(value);
            case GREATER_THAN_OR_EQUAL -> criteriaFilter.setGreaterThanOrEqual(value);
            case GREATER_THAN -> criteriaFilter.setGreaterThan(value);
            case LESS_THAN_OR_EQUAL -> criteriaFilter.setLessThanOrEqual(value);
            case LESS_THAN -> criteriaFilter.setLessThan(value);
        }
        meth.invoke(criteria, criteriaFilter);
    }

    private static void setNumericComparison(NumericFilter filter, Criteria criteria, RangeFilter criteriaFilter, Number value, Method meth) throws InvocationTargetException, IllegalAccessException {
        switch (filter.getComparison()) {
            case EQUAL -> criteriaFilter.setEquals(value);
            case GREATER_THAN_OR_EQUAL -> criteriaFilter.setGreaterThanOrEqual((Comparable) value);
            case GREATER_THAN -> criteriaFilter.setGreaterThan((Comparable) value);
            case LESS_THAN_OR_EQUAL -> criteriaFilter.setLessThanOrEqual((Comparable) value);
            case LESS_THAN -> criteriaFilter.setLessThan((Comparable) value);
        }
        meth.invoke(criteria, criteriaFilter);
    }
}
