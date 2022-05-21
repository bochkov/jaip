package com.sergeybochkov.jaip.model.pdf.validator;

import java.io.IOException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.itextpdf.text.pdf.PdfReader;
import org.springframework.web.multipart.MultipartFile;

public final class PdfFileValidator implements ConstraintValidator<PdfFile, MultipartFile> {
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
