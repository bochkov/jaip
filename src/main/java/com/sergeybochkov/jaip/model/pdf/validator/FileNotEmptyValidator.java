package com.sergeybochkov.jaip.model.pdf.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public final class FileNotEmptyValidator implements ConstraintValidator<FileNotEmpty, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        return multipartFile != null && !multipartFile.isEmpty();
    }
}
