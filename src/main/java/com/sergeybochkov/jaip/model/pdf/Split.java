package com.sergeybochkov.jaip.model.pdf;

import com.sergeybochkov.jaip.model.pdf.validator.FileNotEmpty;
import com.sergeybochkov.jaip.model.pdf.validator.Pages;
import com.sergeybochkov.jaip.model.pdf.validator.PdfFile;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@ToString(of = {"file", "pages", "singleFile"})
public final class Split {

    @FileNotEmpty
    @PdfFile
    private MultipartFile file;
    @Pages
    private String pages;
    private Boolean singleFile;
    private Boolean success;
    private String filename;

}
