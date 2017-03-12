package de.daxu.swamp.common.validator;

public class BasicValidator<T> implements Validator<T> {

    private final Validator<T> function;

    public BasicValidator(Validator<T> function) {
        this.function = function;
    }

    @Override
    public boolean isValid(T object) {
        return function.isValid(object);
    }
}
