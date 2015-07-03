package com.sergeybochkov.jaip.model.pdf.validator;

import com.lowagie.text.pdf.PdfReader;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.util.List;

public class PdfFilesValidator implements ConstraintValidator<PdfFile, List<MultipartFile>> {
    @Override
    public void initialize(PdfFile annotation) {
    }

    @Override
    public boolean isValid(List<MultipartFile> multipartFileList, ConstraintValidatorContext constraintValidatorContext) {
        for (MultipartFile file : multipartFileList) {
            try {
                PdfReader reader = new PdfReader(file.getInputStream());
                reader.getNumberOfPages();
                reader.close();
            } catch (IOException ex) {
                return false;
            }
        }
        return true;
    }
}
