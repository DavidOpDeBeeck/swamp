package de.daxu.swamp.common.comparator;

import de.daxu.swamp.common.Identifiable;
import de.daxu.swamp.common.ValueObject;
import org.apache.commons.lang.builder.CompareToBuilder;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReflectionComparator implements Comparator<Object> {

    public static Comparator<Object> byReflection(String... excludedFields) {
        return new ReflectionComparator(excludedFields.length > 0 ? excludedFields : DEFAULT_EXCLUDED_FIELDS);
    }

    private static final String[] DEFAULT_EXCLUDED_FIELDS = {"id"};

    private final String[] excludedFields;

    public ReflectionComparator(String[] excludedFields) {
        this.excludedFields = excludedFields;
    }

    @Override
    public int compare(Object lhs, Object rhs) {
        List<Object> lhsFields = fieldValuesOf(lhs).collect(Collectors.toList());
        List<Object> rhsFields = fieldValuesOf(rhs).collect(Collectors.toList());

        Iterator<Object> lhsIterator = lhsFields.iterator();
        Iterator<Object> rhsIterator = rhsFields.iterator();

        CompareToBuilder compareToBuilder = new CompareToBuilder();

        while (lhsIterator.hasNext()) {
            Object lhsValue = lhsIterator.next();
            Object rhsValue = rhsIterator.next();
            compareToBuilder.append(lhsValue, rhsValue);
        }

        return compareToBuilder.toComparison();
    }

    private Stream<Object> fieldValuesOf(Object obj) {
        if (obj instanceof Collection)
            return valuesOfCollection(obj);
        if (obj instanceof Map)
            return valuesOfMap(obj);
        if (isValidClass(obj))
            return valuesOfObject(obj);
        return Stream.of(obj);
    }

    private Stream<Object> valuesOfObject(Object obj) {
        List<Field> fields = getFields(obj);

        return fields.stream()
                .filter(this::isIncludedField)
                .map(field -> getFieldValue(field, obj))
                .map(this::fieldValuesOf)
                .flatMap(Function.identity());
    }

    private Stream<Object> valuesOfCollection(Object obj) {
        Collection<?> collection = (Collection<?>) obj;

        return collection.stream()
                .map(this::fieldValuesOf)
                .flatMap(Function.identity());
    }

    private Stream<Object> valuesOfMap(Object obj) {
        Map<?, ?> map = (Map<?, ?>) obj;

        return map.entrySet()
                .stream()
                .map(entry -> Stream.of(this.fieldValuesOf(entry.getKey()), this.fieldValuesOf(entry.getValue())))
                .flatMap(Function.identity())
                .flatMap(Function.identity());
    }

    private boolean isValidClass(Object obj) {
        return obj instanceof Identifiable || obj instanceof ValueObject;
    }

    private boolean isIncludedField(Field field) {
        return Arrays.stream(excludedFields)
                .noneMatch(excludedField ->
                        field.getName().equals(excludedField));
    }

    private Object getFieldValue(Field field, Object obj) {
        try {
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private List<Field> getFields(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();

        return Arrays.stream(fields)
                .collect(Collectors.toList());
    }

}
