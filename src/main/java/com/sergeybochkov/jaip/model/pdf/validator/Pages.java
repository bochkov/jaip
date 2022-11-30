package com.sergeybochkov.jaip.model.pdf.validator;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PagesValidator.class)
public @interface Pages {

    String message() default "Неправильный формат поля";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
