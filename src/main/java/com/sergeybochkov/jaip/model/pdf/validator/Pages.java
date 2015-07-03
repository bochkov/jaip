package com.sergeybochkov.jaip.model.pdf.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PagesValidator.class)
public @interface Pages {

    String message() default "Неправильный формат поля";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
