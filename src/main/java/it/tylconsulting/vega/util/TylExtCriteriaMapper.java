package it.tylconsulting.vega.util;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.filter.Comparison;
import ch.ralscha.extdirectspring.filter.Filter;
import ch.ralscha.extdirectspring.filter.NumericFilter;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class TylExtCriteriaMapper {
    static Logger logger = LoggerFactory.getLogger(TylExtCriteriaMapper.class);

    public static TylRequestParams transformRequest(ExtDirectStoreReadRequest request){
        TylRequestParams trq = new TylRequestParams();
        trq.setSize(request.getLimit());
        trq.setPage(request.getPage()-1);
        trq.setSort(TylUtil.createSort(request.getSorters()));
        return trq;
    }
    public static Criteria mapCriterias(ExtDirectStoreReadRequest request, Criteria criteria, Class<?> entityClass) {
        Criteria myCriteria = criteria;
        List<Filter> filters= request.getFilters();
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

    private static Criteria setFilter(Filter filter, Criteria criteria, Class<?> entityClass) throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException, ParseException, ClassNotFoundException, InstantiationException {
        Field criteriaField = criteria.getClass().getDeclaredField(filter.getField());
        String fieldType = criteriaField.getType().getSimpleName();
        Class<?> fieldClass = criteriaField.getType();

        String methodName = "set" + StringUtils.capitalize(filter.getField());
        Method meth;
        switch (fieldType) {
            case "StringFilter" -> {
                ch.ralscha.extdirectspring.filter.StringFilter ralschaStringFilter = (ch.ralscha.extdirectspring.filter.StringFilter) filter;
                String filterValue = ralschaStringFilter.getValue();
                StringFilter stringFilter = new StringFilter();
                switch (ralschaStringFilter.getRawComparison()) {
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
                ch.ralscha.extdirectspring.filter.NumericFilter ralschaBooleanFilter = (ch.ralscha.extdirectspring.filter.NumericFilter) filter;
                boolean booleanRalschaFilter = ((Integer) ralschaBooleanFilter.getValue()) == 1 ? true : false;
                Boolean filterValue = Boolean.valueOf(booleanRalschaFilter);
                BooleanFilter booleanFilter = new BooleanFilter();
                if (ralschaBooleanFilter.getComparison().equals(Comparison.EQUAL))
                    booleanFilter.setEquals(filterValue);
                else
                    booleanFilter.setNotEquals(filterValue);
                meth = criteria.getClass().getMethod(methodName, BooleanFilter.class);
                meth.invoke(criteria, booleanFilter);
            }
            case "ShortFilter" -> {
                NumericFilter numFilter = (NumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                ShortFilter criteriaFilter = new ShortFilter();
                mapNumberParameters((NumericFilter) filter, ShortFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "IntegerFilter" -> {
                NumericFilter numFilter = (NumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                IntegerFilter criteriaFilter = new IntegerFilter();
                mapNumberParameters((NumericFilter) filter, IntegerFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "LongFilter" -> {
                NumericFilter numFilter = (NumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                LongFilter criteriaFilter = new LongFilter();
                mapNumberParameters((NumericFilter) filter, LongFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "FloatFilter" -> {
                NumericFilter numFilter = (NumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                FloatFilter criteriaFilter = new FloatFilter();
                mapNumberParameters((NumericFilter) filter, FloatFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "DoubleFilter" -> {
                NumericFilter numFilter = (NumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                DoubleFilter criteriaFilter = new DoubleFilter();
                mapNumberParameters((NumericFilter) filter, DoubleFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "BigDecimalFilter" -> {
                NumericFilter numFilter = (NumericFilter) filter;
                String filterValue = numFilter.getValue().toString();
                BigDecimalFilter criteriaFilter = new BigDecimalFilter();
                mapNumberParameters((NumericFilter) filter, BigDecimalFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "LocalDateFilter" -> {
                ch.ralscha.extdirectspring.filter.StringFilter dateFilter = (ch.ralscha.extdirectspring.filter.StringFilter) filter;
                String filterValue = dateFilter.getValue();
                LocalDateFilter criteriaFilter = new LocalDateFilter();
                mapDateParameters(dateFilter, LocalDateFilter.class, filterValue, criteriaFilter, criteria);
            }
            case "ZonedDateFilter" -> {
                ch.ralscha.extdirectspring.filter.StringFilter dateFilter = (ch.ralscha.extdirectspring.filter.StringFilter) filter;
                String filterValue = dateFilter.getValue();
                ZonedDateTimeFilter criteriaFilter = new ZonedDateTimeFilter();
                mapDateParameters(dateFilter, ZonedDateTimeFilter.class, filterValue, criteriaFilter, criteria);
            }
            default -> { // utilizzato per gli enum
                // Prendo il valore selezionato, che ricevo come String
                ch.ralscha.extdirectspring.filter.StringFilter ralschaStringFilter = (ch.ralscha.extdirectspring.filter.StringFilter) filter;
                String filterSelectionStringValue = ralschaStringFilter.getValue();

                // Determino il type del parametro usato (di tipo enum)
                Class<?> paramFieldType= entityClass.getDeclaredField(filter.getField()).getType();

                // Verifico se è veramente un enum
                if (paramFieldType.isEnum()) {
                    // E' un Enum, quindi Creo la risposta come enum
//                    Method valueOfEnum = paramFieldType.getMethod("valueOf", String.class);
//                    Object enumField = valueOfEnum.invoke(paramFieldType, filterSelectionStringValue);

                    // Determinazione del nome istanza del filtro, che è una innerclass
                    String canonicalFilterName = criteriaField.getType().getCanonicalName(); //it.tylconsulting.vega.service.criteria.TestEntityCriteria.QuestionnaireTypeFilter
                    String entityClassName = StringUtils.substringBeforeLast(canonicalFilterName, ".");
                    String filterName = StringUtils.substringAfterLast(canonicalFilterName, ".");
                    String filterToInstantiate = entityClassName + "$" + filterName; //it.tylconsulting.vega.service.criteria.TestEntityCriteria$QuestionnaireTypeFilter

                    // Impostazione della inner private class del filtro
                    Class<?> filterClazz=Class.forName(filterToInstantiate);

                    // Creazione di una istanza del filtro
                    Constructor<?> constructor = filterClazz.getConstructor();
                    Object filterObject = constructor.newInstance();

                    // Impostazione del metodo da invocare sul filtro
                    String setter="==";
                    switch(filter.getRawComparison()){
                        case "==" -> setter="setEquals";
                        case "!=" -> setter="setNotEquals";
                        case "empty" -> setter="setNotIn";
                        case "nempty" -> setter="setIn";
                    }
                    if(setter.contains("Equals")) {
                        Method setQueryMethod= filterObject.getClass().getMethod(setter, Object.class);
                        Method valueOfEnum = paramFieldType.getMethod("valueOf", String.class);
                        Object enumField = valueOfEnum.invoke(paramFieldType, filterSelectionStringValue);
                        setQueryMethod.invoke(filterObject, enumField);
                    }
                    else {
                        Method setQueryMethod= filterObject.getClass().getMethod(setter, List.class);
                        Object[] entityFieldArray=paramFieldType.getEnumConstants();
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                LocalDateTime zdate = LocalDateTime.parse(valueInFilter, formatter);
                LocalDate ldItaly = zdate.plus(2, ChronoUnit.HOURS).toLocalDate();
                setLocalDateComparison(filter, criteria, (LocalDateFilter) criteriaFilter, ldItaly, meth);
            }
            case "ZonedDateTimeFilter" -> {
                ZonedDateTime value = ZonedDateTime.parse(valueInFilter).plus(2, ChronoUnit.HOURS);
                setZonedDateTimeComparison(filter, criteria, (ZonedDateTimeFilter) criteriaFilter, value, meth);
            }
        }
    }

    private static void setLocalDateComparison(ch.ralscha.extdirectspring.filter.StringFilter filter, Criteria criteria, LocalDateFilter criteriaFilter, LocalDate value, Method meth) throws InvocationTargetException, IllegalAccessException {
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

    private static void setZonedDateTimeComparison(ch.ralscha.extdirectspring.filter.StringFilter filter, Criteria criteria, ZonedDateTimeFilter criteriaFilter, ZonedDateTime value, Method meth) throws InvocationTargetException, IllegalAccessException {
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

    private static void setNumericComparison(NumericFilter filter, Criteria criteria, RangeFilter criteriaFilter, Number value, Method meth) throws InvocationTargetException, IllegalAccessException {
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
