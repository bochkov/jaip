package com.sergeybochkov.jaip.model.pdf.validator;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public final class FilesNotEmptyValidator implements ConstraintValidator<FileNotEmpty, List<MultipartFile>> {
    @Override
    public boolean isValid(List<MultipartFile> multipartFiles, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFiles.isEmpty())
            return false;
        for (MultipartFile file : multipartFiles)
            if (file.isEmpty())
                return false;
        return true;
    }
}
