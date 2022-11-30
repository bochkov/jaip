package com.sergeybochkov.jaip.model.pdf.validator;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FileNotEmptyValidator.class, FilesNotEmptyValidator.class})
public @interface FileNotEmpty {

    String message() default "Заполните поле";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
