package com.sergeybochkov.jaip.model.pdf.validator;

import com.lowagie.text.pdf.PdfReader;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;

public class PdfFileValidator implements ConstraintValidator<PdfFile, MultipartFile> {
    @Override
    public void initialize(PdfFile annotation) {
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFile == null)
            return false;
        try {
            PdfReader reader = new PdfReader(multipartFile.getInputStream());
            reader.getNumberOfPages();
            reader.close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
}
