package com.sergeybochkov.jaip.model.pdf.validator;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PdfFileValidator.class, PdfFilesValidator.class})
public @interface PdfFile {
    String message() default "Это не pdf-файл";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
