package com.sergeybochkov.jaip.model.pdf;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sergeybochkov.jaip.model.pdf.validator.FileNotEmpty;
import com.sergeybochkov.jaip.model.pdf.validator.PdfFile;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public final class Merge {

    @FileNotEmpty
    @PdfFile
    private ArrayList<MultipartFile> files;
    private String filename;
    private Boolean success;

}
