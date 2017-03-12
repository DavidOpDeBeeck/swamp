package de.daxu.swamp.common.validator;

@FunctionalInterface
public interface Validator<T> {

    boolean isValid(T object);
}
