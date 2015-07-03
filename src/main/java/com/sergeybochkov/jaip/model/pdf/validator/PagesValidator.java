package com.sergeybochkov.jaip.model.pdf.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PagesValidator implements ConstraintValidator<Pages, String> {

    @Override
    public void initialize(Pages annotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return false;

        Pattern pattern = Pattern.compile("(,+|-{2,})"); // one "," or two "-"
        Matcher m = pattern.matcher(value);
        if (m.matches())
            return false;

        pattern = Pattern.compile("[0-9\\-,]+");
        m = pattern.matcher(value);
        return m.matches();
    }
}
