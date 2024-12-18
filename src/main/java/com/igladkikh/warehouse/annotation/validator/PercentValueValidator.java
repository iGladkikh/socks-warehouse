package com.igladkikh.warehouse.annotation.validator;

import com.igladkikh.warehouse.annotation.PercentValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PercentValueValidator implements ConstraintValidator<PercentValue, Integer> {
    private static final int MIN = 0;
    private static final int MAX = 100;
    private int min;
    private int max;

    @Override
    public void initialize(PercentValue annotation) {
        min = annotation.min();
        max = annotation.max();

        if (min < MIN || max > MAX || min >= max) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean isValid(Integer target, ConstraintValidatorContext context) {


        return target == null || (target >= min && target <= max);
    }
}
