package com.example.dnd_13th_9_be.common.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumValid {
  Class<? extends Enum<?>> enumClass();

  String message() default "허용되지 않은 값입니다.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
