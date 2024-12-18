package com.igladkikh.warehouse.annotation;

import javax.validation.Constraint;
import com.igladkikh.warehouse.annotation.validator.PercentValueValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = PercentValueValidator.class)
public @interface PercentValue {
    String message() default "Value must be between {min} and {max}";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
    int min() default 0;
    int max() default 100;
}