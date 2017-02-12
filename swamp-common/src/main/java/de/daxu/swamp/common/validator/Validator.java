package de.daxu.swamp.common.validator;

@FunctionalInterface
public interface Validator<T> {

    boolean validate(T object);

    default boolean invalidate(T object) {
        return !validate(object);
    }
}
