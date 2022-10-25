package it.tylconsulting.vega.util;

import ch.ralscha.extdirectspring.bean.SortDirection;
import ch.ralscha.extdirectspring.bean.SortInfo;
import ch.ralscha.extdirectspring.filter.DateFilter;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.NumericFilter;
import org.springframework.data.domain.Sort;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.List;

public class TylUtil {
 /*   public static Criteria mapCriterias(List<Filter> filters, Criteria criteria) {
        Criteria myCriteria = criteria;
        filters.forEach(filter -> {
            try {
                setByType(filter, myCriteria);
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

    private static void setByType(Filter filter, Criteria criteria) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        switch (filter.getClass().getSimpleName()) {
            case "StringFilter" -> mapStringFilter((ch.ralscha.extdirectspring.filter.StringFilter) filter, criteria);
            case "NumericFilter" ->
                mapNumericFilter((ch.ralscha.extdirectspring.filter.NumericFilter) filter, criteria);
            case "BooleanFilter" ->
                mapBooleanFilter((ch.ralscha.extdirectspring.filter.BooleanFilter) filter, criteria);
            case "DateFilter" -> mapDateFilter((ch.ralscha.extdirectspring.filter.DateFilter) filter, criteria);
            //case "ListFilter" -> mapListFilter((ch.ralscha.extdirectspring.filter.DateFilter) filter, criteria);
        }
    }

    private static void mapStringFilter(ch.ralscha.extdirectspring.filter.StringFilter filter, Criteria criteria) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "set" + capitalize(filter.getField());
        Method meth = criteria.getClass().getMethod(methodName, StringFilter.class);
        StringFilter sf = new StringFilter();
        sf.setContains(filter.getValue());
        meth.invoke(criteria, sf);
    }

    private static void mapNumericFilter(NumericFilter filter, Criteria criteria) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Field f = criteria.getClass().getDeclaredField(filter.getField());
        String numericType = f.getType().getSimpleName();
        String filterValue = filter.getValue().toString();
        switch (numericType) {
            case "ShortFilter" -> {
                ShortFilter criteriaFilter = new ShortFilter();
                mapNumberParameters(filter, ShortFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "IntegerFilter" -> {
                IntegerFilter criteriaFilter = new IntegerFilter();
                mapNumberParameters(filter, IntegerFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "LongFilter" -> {
                LongFilter criteriaFilter = new LongFilter();
                mapNumberParameters(filter, LongFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "FloatFilter" -> {
                FloatFilter criteriaFilter = new FloatFilter();
                mapNumberParameters(filter, FloatFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "DoubleFilter" -> {
                DoubleFilter criteriaFilter = new DoubleFilter();
                mapNumberParameters(filter, DoubleFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "BigDecimalFilter" -> {
                BigDecimalFilter criteriaFilter = new BigDecimalFilter();
                mapNumberParameters(filter, BigDecimalFilter.class, filterValue, criteriaFilter, criteria);
            }
        }
    }

    private static void mapBooleanFilter(ch.ralscha.extdirectspring.filter.BooleanFilter filter, Criteria criteria) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "set" + capitalize(filter.getField());
        Method meth = criteria.getClass().getMethod(methodName, BooleanFilter.class);
        BooleanFilter sf = new BooleanFilter();
        sf.setEquals(filter.getValue());
        meth.invoke(criteria, sf);
    }


    private static void mapDateFilter(DateFilter filter, Criteria criteria) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        Field f = criteria.getClass().getDeclaredField(filter.getField());
        String dateType = f.getType().getSimpleName();
        String filterValue = filter.getValue();
        switch (dateType) {
            case "LocalDateFilter" -> {
                LocalDateFilter criteriaFilter = new LocalDateFilter();
                mapDateParameters(filter, LocalDateFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "ZonedDateFilter" -> {
                ZonedDateTimeFilter criteriaFilter = new ZonedDateTimeFilter();
                mapDateParameters(filter, ZonedDateTimeFilter.class, filterValue, criteriaFilter, criteria);
            }
        }
    }

    private static void mapDateParameters(DateFilter filter, Class<?> filterClass, String valueInFilter, RangeFilter<?> criteriaFilter, Criteria criteria) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "set" + capitalize(filter.getField());
        Method meth = criteria.getClass().getMethod(methodName, filterClass);

        switch (criteriaFilter.getClass().getSimpleName()) {
            case "LocalDateFilter" -> {
                LocalDate value = LocalDate.parse(valueInFilter);
                setDateComparison(filter, criteria, criteriaFilter, value, meth);
            }
            case "ZonedDateTimeFilter" -> {
                ZonedDateTime value = ZonedDateTime.parse(valueInFilter);
                setDateComparison(filter, criteria, criteriaFilter, value, meth);
            }
        }
    }


    private static void mapListFilter(DateFilter filter, Criteria criteria) {
    }


    private static void mapDurationParameters(NumericFilter filter, Class<DurationFilter> durationFilterClass, String filterValue, DurationFilter criteriaFilter, Criteria criteria) {
    }

    private static void mapInstantParameters(NumericFilter filter, Class<InstantFilter> instantFilterClass, String filterValue, InstantFilter criteriaFilter, Criteria criteria) {
    }

    private static void mapZoneDateParameters(NumericFilter filter, Class<ZonedDateTimeFilter> zonedDateTimeFilterClass, String filterValue, ZonedDateTimeFilter criteriaFilter, Criteria criteria) {
    }


    private static void mapUUIDParameters(NumericFilter filter, Class<UUIDFilter> uuidFilterClass, String filterValue, UUIDFilter criteriaFilter, Criteria criteria) {
    }

    private static void mapNumberParameters(NumericFilter filter, Class<?> filterClass, String valueInFilter, RangeFilter<?> criteriaFilter, Criteria criteria) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "set" + capitalize(filter.getField());
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

    private static void setDateComparison(DateFilter filter, Criteria criteria, RangeFilter criteriaFilter, Temporal value, Method meth) throws InvocationTargetException, IllegalAccessException {
        switch (filter.getComparison()) {
            case EQUAL -> criteriaFilter.setEquals(value);
            case GREATER_THAN_OR_EQUAL -> criteriaFilter.setGreaterThanOrEqual((Comparable) value);
            case GREATER_THAN -> criteriaFilter.setGreaterThan((Comparable) value);
            case LESS_THAN_OR_EQUAL -> criteriaFilter.setLessThanOrEqual((Comparable) value);
            case LESS_THAN -> criteriaFilter.setLessThan((Comparable) value);
        }
        meth.invoke(criteria, criteriaFilter);
    }*/


    //region Creazione sortable
    public static Sort createSort(List<SortInfo> sortInfos) {
        Sort sort = Sort.unsorted();
        boolean alreadyAssigned = false;
        if (sortInfos.size() == 0)
            return sort;
        else
            for (SortInfo si : sortInfos) {
                if (alreadyAssigned) {
                    sort = sort.and(setSort(sort, si));
                } else {
                    sort = setSort(sort, si);
                    alreadyAssigned = true;
                }
            }
        return sort;
    }

    private static Sort setSort(Sort sort, SortInfo si) {
        sort = Sort.by(si.getProperty());
        if (si.getDirection().equals(SortDirection.ASCENDING))
            sort = sort.ascending();
        else
            sort = sort.descending();
        return sort;
    }

    //endregion

    public static final String capitalize(String str) {
        if (str == null || str.length() == 0) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
