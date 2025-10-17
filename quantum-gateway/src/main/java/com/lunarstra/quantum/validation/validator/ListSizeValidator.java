package com.lunarstra.quantum.validation.validator;

import com.lunarstra.quantum.validation.annotation.ListLength;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

/**
 * @author Simons
 */
public class ListSizeValidator implements ConstraintValidator<ListLength, List<?>> {

    private int min;

    private int max;

    @Override
    public void initialize(ListLength constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(List<?> list, ConstraintValidatorContext constraintValidatorContext) {
        if (list == null) {
            return true; // null values should be validated by @NotNull or @NotEmpty
        }

        int size = list.size();
        return size >= min && size <= max;
    }
}
