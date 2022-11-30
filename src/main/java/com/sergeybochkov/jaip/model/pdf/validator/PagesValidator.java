package com.sergeybochkov.jaip.model.pdf.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public final class PagesValidator implements ConstraintValidator<Pages, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return false;

        Pattern pattern = Pattern.compile("(,+|-{2,})"); // one "," or two "-"
        Matcher m = pattern.matcher(value);
        if (m.matches())
            return false;

        pattern = Pattern.compile("[\\d\\-,]+");
        m = pattern.matcher(value);
        return m.matches();
    }
}
