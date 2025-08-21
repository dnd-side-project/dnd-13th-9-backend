package com.example.dnd_13th_9_be.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValid, Enum> {
    private EnumValid annotation;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(java.lang.Enum value, ConstraintValidatorContext context) {
        if (value == null) return true;
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        for (Object enumValue : enumValues) {
            if (value.name().equals(enumValue.toString())) return true;
        }
        return false;
    }
}
