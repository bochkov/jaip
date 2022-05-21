package com.sergeybochkov.jaip.model.pdf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sergeybochkov.jaip.model.pdf.validator.FileNotEmpty;
import com.sergeybochkov.jaip.model.pdf.validator.PdfFile;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public final class Compress {

    @FileNotEmpty
    @PdfFile
    private MultipartFile file;
    private String filename;
    private String message;
    private Double compressRate;
    private Boolean success;

}
