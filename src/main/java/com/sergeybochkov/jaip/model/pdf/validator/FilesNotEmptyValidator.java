package com.sergeybochkov.jaip.model.pdf.validator;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class FilesNotEmptyValidator implements ConstraintValidator<FileNotEmpty, List<MultipartFile>> {
    @Override
    public void initialize(FileNotEmpty annotation) {
    }

    @Override
    public boolean isValid(List<MultipartFile> multipartFiles, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFiles.size() == 0)
            return false;
        for (MultipartFile file : multipartFiles)
            if (file.isEmpty())
                return false;
        return true;
    }

}
