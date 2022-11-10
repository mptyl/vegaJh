package it.tylconsulting.vega.util.extjshelpers.filters;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.*;
import static org.springframework.util.StringUtils.capitalize;

public class TylExtCriteriaMapper {
    static Logger logger = LoggerFactory.getLogger(TylExtCriteriaMapper.class);

    public static List<TylFilter> transformFilter(List<FilterParam> extjsFilters, Class<?> entityClass) {
        return extjsFilters.stream().map(filter -> {
            try {
                return toTylFilter(filter, entityClass);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    private static TylFilter toTylFilter(FilterParam ejs, Class<?> entityClass) throws NoSuchFieldException {
        String property = ejs.getProperty();
        Field propertyField = entityClass.getDeclaredField(property);
        String fieldType = propertyField.getType().getSimpleName();
        switch (fieldType) {
            case "Boolean" -> {
                return new TylBooleanFilter(ejs.getProperty(), (Integer) ejs.getValue() == 1 ? true : false, ejs.getOperator());
            }
            case "Short" -> {
                return new TylNumericFilter(ejs.getProperty(), ((Integer)ejs.getValue()).shortValue(), ejs.getOperator());
            }
            case "Integer" -> {
                return new TylNumericFilter(ejs.getProperty(), (Integer)ejs.getValue(), ejs.getOperator());
            }
            case "Long", "Duration" -> {
                return new TylNumericFilter(ejs.getProperty(), ((Number)ejs.getValue()).longValue(), ejs.getOperator());
            }
            case "Float" -> {
                return new TylNumericFilter(ejs.getProperty(), ((Number)ejs.getValue()).floatValue(), ejs.getOperator());
            }
            case "Double" -> {
                return new TylNumericFilter(ejs.getProperty(), ((Number)ejs.getValue()).doubleValue(), ejs.getOperator());
            }
            case "BigDecimal" -> {
                return new TylNumericFilter(ejs.getProperty(), BigDecimal.valueOf(((Number)ejs.getValue()).doubleValue()), ejs.getOperator());
            }
            default -> { // compresi String, UUID e le Date
                return new TylStringFilter(ejs.getProperty(), (String)ejs.getValue(), ejs.getOperator());
            }
        }
    }

    public static Criteria mapCriterias(List<TylFilter> filters, Criteria criteria, Class<?> entityClass) {
        Criteria myCriteria = criteria;
        filters.forEach(filter -> {
            try {
                setFilter(filter, myCriteria, entityClass);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            }
        });
        return myCriteria;
    }

    private static Criteria setFilter(TylFilter filter, Criteria criteria, Class<?> entityClass) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, ParseException, ClassNotFoundException, InstantiationException {
        Field criteriaField = criteria.getClass().getDeclaredField(filter.getField());
        String fieldType = criteriaField.getType().getSimpleName();
        Class<?> fieldClass = criteriaField.getType();

        String methodName = "set" + StringUtils.capitalize(filter.getField());
        Method meth;
        switch (fieldType) {
            case "StringFilter" -> {
                TylStringFilter tylStringFilter = (TylStringFilter) filter;
                String filterValue = tylStringFilter.getValue();
                StringFilter stringFilter = new StringFilter();
                switch (tylStringFilter.getRawComparison()) {
                    case "like" -> stringFilter.setContains(filterValue);
                    case "==", "eq" -> stringFilter.setEquals(filterValue);
                    case "!=", "neq" -> stringFilter.setNotEquals(filterValue);
                    case "empty" -> stringFilter.setEquals("");
                    case "nempty" -> stringFilter.setNotEquals("");
                }
                meth = criteria.getClass().getMethod(methodName, StringFilter.class);
                meth.invoke(criteria, stringFilter);
            }
            case "BooleanFilter" -> {
                TylBooleanFilter tylBooleanFilter = (TylBooleanFilter) filter;
                 BooleanFilter booleanFilter = new BooleanFilter();
                if (tylBooleanFilter.getRawComparison().equals("=="))
                    booleanFilter.setEquals(tylBooleanFilter.getValue());
                else
                    booleanFilter.setNotEquals(tylBooleanFilter.getValue());
                meth = criteria.getClass().getMethod(methodName, BooleanFilter.class);
                meth.invoke(criteria, booleanFilter);
            }
            case "ShortFilter" -> {
                TylNumericFilter numFilter = (TylNumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                ShortFilter criteriaFilter = new ShortFilter();
                mapNumberParameters((TylNumericFilter) filter, ShortFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "IntegerFilter" -> {
                TylNumericFilter numFilter = (TylNumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                IntegerFilter criteriaFilter = new IntegerFilter();
                mapNumberParameters((TylNumericFilter) filter, IntegerFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "LongFilter" -> {
                TylNumericFilter numFilter = (TylNumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                LongFilter criteriaFilter = new LongFilter();
                mapNumberParameters((TylNumericFilter) filter, LongFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "DurationFilter" -> {
                TylNumericFilter numFilter = (TylNumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                DurationFilter criteriaFilter = new DurationFilter();
                mapNumberParameters((TylNumericFilter) filter, DurationFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "FloatFilter" -> {
                TylNumericFilter numFilter = (TylNumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                FloatFilter criteriaFilter = new FloatFilter();
                mapNumberParameters((TylNumericFilter) filter, FloatFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "DoubleFilter" -> {
                TylNumericFilter numFilter = (TylNumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                DoubleFilter criteriaFilter = new DoubleFilter();
                mapNumberParameters((TylNumericFilter) filter, DoubleFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "BigDecimalFilter" -> {
                TylNumericFilter numFilter = (TylNumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                BigDecimalFilter criteriaFilter = new BigDecimalFilter();
                mapNumberParameters((TylNumericFilter) filter, BigDecimalFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "LocalDateFilter" -> {
                TylStringFilter dateFilter = (TylStringFilter) filter;
                String filterValue = dateFilter.getValue();
                LocalDateFilter criteriaFilter = new LocalDateFilter();
                mapDateParameters(dateFilter, LocalDateFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "ZonedDateTimeFilter" -> {
                TylStringFilter dateFilter = (TylStringFilter) filter;
                String filterValue = dateFilter.getValue();
                ZonedDateTimeFilter criteriaFilter = new ZonedDateTimeFilter();
                mapDateParameters(dateFilter, ZonedDateTimeFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "InstantFilter" -> {
                TylStringFilter instantFilter = (TylStringFilter) filter;
                String filterValue = instantFilter.getValue();
                InstantFilter criteriaFilter = new InstantFilter();
                mapDateParameters(instantFilter, InstantFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "UUIDFilter" -> {
                TylStringFilter tylStringFilter = (TylStringFilter) filter;
                String filterValue = tylStringFilter.getValue();
                UUIDFilter uuidFilter = new UUIDFilter();
                switch (tylStringFilter.getRawComparison()) {
                    case "==", "eq" -> uuidFilter.setEquals(UUID.fromString(filterValue));
                    case "!=", "neq" -> uuidFilter.setNotEquals(UUID.fromString(filterValue));
                    case "empty" -> uuidFilter.setEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"));
                    case "nempty" -> uuidFilter.setNotEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"));
                }
                meth = criteria.getClass().getMethod(methodName, UUIDFilter.class);
                meth.invoke(criteria, uuidFilter);
            }
            default -> { // utilizzato per gli enum
                // Prendo il valore selezionato, che ricevo come String
                TylStringFilter tylStringFilter = (TylStringFilter) filter;
                String filterSelectionStringValue = tylStringFilter.getValue();

                // Determino il type del parametro usato (di tipo enum)
                Class<?> paramFieldType = entityClass.getDeclaredField(filter.getField()).getType();

                // Verifico se è veramente un enum
                if (paramFieldType.isEnum()) {
                    // E' un Enum, quindi Creo la risposta come enum
                    // Determinazione del nome istanza del filtro, che è una innerclass
                    String canonicalFilterName = criteriaField.getType().getCanonicalName(); //it.tylconsulting.vega.service.criteria.TestEntityCriteria.QuestionnaireTypeFilter
                    String entityClassName = StringUtils.substringBeforeLast(canonicalFilterName, ".");
                    String filterName = StringUtils.substringAfterLast(canonicalFilterName, ".");
                    String filterToInstantiate = entityClassName + "$" + filterName; //it.tylconsulting.vega.service.criteria.TestEntityCriteria$QuestionnaireTypeFilter

                    // Impostazione della inner private class del filtro
                    Class<?> filterClazz = Class.forName(filterToInstantiate);

                    // Creazione di una istanza del filtro
                    Constructor<?> constructor = filterClazz.getConstructor();
                    Object filterObject = constructor.newInstance();

                    // Impostazione del metodo da invocare sul filtro
                    String setter = "==";
                    switch (filter.getRawComparison()) {
                        case "==" -> setter = "setEquals";
                        case "!=" -> setter = "setNotEquals";
                        case "empty" -> setter = "setNotIn";
                        case "nempty" -> setter = "setIn";
                    }
                    if (setter.contains("Equals")) {
                        Method setQueryMethod = filterObject.getClass().getMethod(setter, Object.class);
                        Method valueOfEnum = paramFieldType.getMethod("valueOf", String.class);
                        Object enumField = valueOfEnum.invoke(paramFieldType, filterSelectionStringValue);
                        setQueryMethod.invoke(filterObject, enumField);
                    } else {
                        Method setQueryMethod = filterObject.getClass().getMethod(setter, List.class);
                        Object[] entityFieldArray = paramFieldType.getEnumConstants();
                        setQueryMethod.invoke(filterObject, Arrays.stream(entityFieldArray).toList());
                    }

                    //  Invocazione del metodo sul criteria
                    meth = criteria.getClass().getMethod(methodName, filterClazz);
                    meth.invoke(criteria, filterObject);
                } else
                    logger.info(paramFieldType + " non è un enum, nessun filtro può essere applicato");
            }

        }
        return criteria;
    }

    private static void mapUUIDParameters(TylStringFilter uuidFilter, Class<UUID> uuidClass, String filterValue, UUIDFilter criteriaFilter, Criteria criteria) {

    }

    private static void mapNumberParameters(TylNumericFilter filter, Class<?> filterClass, String valueInFilter, RangeFilter<?> criteriaFilter, Criteria criteria) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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
            case "DurationFilter" -> {
                Duration value = Duration.of(Long.valueOf(valueInFilter), ChronoUnit.SECONDS);
                setDurationComparison(filter, criteria, (DurationFilter) criteriaFilter, value, meth);
            }
        }
    }

    private static void mapDateParameters(TylStringFilter filter, Class<?> filterClass, String valueInFilter, RangeFilter<?> criteriaFilter, Criteria criteria) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "set" + capitalize(filter.getField());
        Method meth = criteria.getClass().getMethod(methodName, filterClass);

        switch (criteriaFilter.getClass().getSimpleName()) {
            case "LocalDateFilter" -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                LocalDateTime zdate = LocalDateTime.parse(valueInFilter, formatter);
                LocalDate ldItaly = zdate.plus(2, HOURS).toLocalDate();
                setLocalDateComparison(filter, criteria, (LocalDateFilter) criteriaFilter, ldItaly, meth);
            }
            case "ZonedDateTimeFilter" -> {
                ZonedDateTime value = ZonedDateTime.parse(valueInFilter).plus(2, HOURS);
                setZonedDateTimeComparison(filter, criteria, (ZonedDateTimeFilter) criteriaFilter, value, meth);
            }
            case "InstantFilter" -> {
                Instant value = Instant.parse(valueInFilter);
                setInstantComparison(filter, criteria, (InstantFilter) criteriaFilter, value, meth);
            }
        }
    }

    private static void setInstantComparison(TylStringFilter filter, Criteria criteria, InstantFilter criteriaFilter, Instant value, Method meth) throws InvocationTargetException, IllegalAccessException {
        switch (filter.getRawComparison()) {
            case "==", "eq" -> criteriaFilter.setEquals(value);
            case "!=", "neq" -> criteriaFilter.setNotEquals(value);
            case ">=", "gte" -> criteriaFilter.setGreaterThanOrEqual(value);
            case ">", "gt" -> criteriaFilter.setGreaterThan(value);
            case "<=", "lte" -> criteriaFilter.setLessThanOrEqual(value);
            case "<", "lt" -> criteriaFilter.setLessThan(value);
        }
        meth.invoke(criteria, criteriaFilter);
    }

    private static void setLocalDateComparison(TylStringFilter filter, Criteria criteria, LocalDateFilter criteriaFilter, LocalDate value, Method meth) throws InvocationTargetException, IllegalAccessException {
        switch (filter.getRawComparison()) {
            case "==", "eq" -> criteriaFilter.setEquals(value);
            case "!=", "neq" -> criteriaFilter.setNotEquals(value);
            case ">=", "gte" -> criteriaFilter.setGreaterThanOrEqual(value);
            case ">", "gt" -> criteriaFilter.setGreaterThan(value);
            case "<=", "lte" -> criteriaFilter.setLessThanOrEqual(value);
            case "<", "lt" -> criteriaFilter.setLessThan(value);
        }
        meth.invoke(criteria, criteriaFilter);
    }

    private static void setZonedDateTimeComparison(TylStringFilter filter, Criteria criteria, ZonedDateTimeFilter criteriaFilter, ZonedDateTime value, Method meth) throws InvocationTargetException, IllegalAccessException {
        switch (filter.getRawComparison()) {
            case "==", "eq" -> criteriaFilter.setEquals(value);
            case "!=", "neq" -> criteriaFilter.setNotEquals(value);
            case ">=", "gte" -> criteriaFilter.setGreaterThanOrEqual(value);
            case ">", "gt" -> criteriaFilter.setGreaterThan(value);
            case "<=", "lte" -> criteriaFilter.setLessThanOrEqual(value);
            case "<", "lt" -> criteriaFilter.setLessThan(value);
        }
        meth.invoke(criteria, criteriaFilter);
    }

    private static void setDurationComparison(TylNumericFilter filter, Criteria criteria, DurationFilter criteriaFilter, Duration value, Method meth) throws InvocationTargetException, IllegalAccessException {
        switch (filter.getRawComparison()) {
            case "==", "eq" -> criteriaFilter.setEquals(value);
            case "!=", "neq" -> criteriaFilter.setNotEquals(value);
            case ">=", "gte" -> criteriaFilter.setGreaterThanOrEqual(value);
            case ">", "gt" -> criteriaFilter.setGreaterThan(value);
            case "<=", "lte" -> criteriaFilter.setLessThanOrEqual(value);
            case "<", "lt" -> criteriaFilter.setLessThan(value);
        }
        meth.invoke(criteria, criteriaFilter);
    }

    private static void setNumericComparison(TylNumericFilter filter, Criteria criteria, RangeFilter criteriaFilter, Number value, Method meth) throws InvocationTargetException, IllegalAccessException {
        switch (filter.getRawComparison()) {
            case "==", "eq" -> criteriaFilter.setEquals(value);
            case "!=", "neq" -> criteriaFilter.setNotEquals(value);
            case ">=", "gte" -> criteriaFilter.setGreaterThanOrEqual((Comparable) value);
            case ">", "gt" -> criteriaFilter.setGreaterThan((Comparable) value);
            case "<=", "lte" -> criteriaFilter.setLessThanOrEqual((Comparable) value);
            case "<", "lt" -> criteriaFilter.setLessThan((Comparable) value);
        }
        meth.invoke(criteria, criteriaFilter);
    }

}
