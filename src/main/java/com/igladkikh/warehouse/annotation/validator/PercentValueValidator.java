package com.igladkikh.warehouse.annotation.validator;

import com.igladkikh.warehouse.annotation.PercentValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PercentValueValidator implements ConstraintValidator<PercentValue, Integer> {

    @Override
    public void initialize(PercentValue annotation) {
    }

    @Override
    public boolean isValid(Integer target, ConstraintValidatorContext context) {
        final int from = 0;
        final int to = 100;

        return target == null || (target >= from && target <= to);
    }
}
