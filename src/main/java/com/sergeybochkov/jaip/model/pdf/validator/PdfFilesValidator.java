package com.sergeybochkov.jaip.model.pdf.validator;

import java.io.IOException;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.itextpdf.text.pdf.PdfReader;
import org.springframework.web.multipart.MultipartFile;

public final class PdfFilesValidator implements ConstraintValidator<PdfFile, List<MultipartFile>> {
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
