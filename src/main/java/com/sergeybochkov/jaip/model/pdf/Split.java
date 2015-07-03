package com.sergeybochkov.jaip.model.pdf;

import com.sergeybochkov.jaip.model.pdf.validator.FileNotEmpty;
import com.sergeybochkov.jaip.model.pdf.validator.Pages;
import com.sergeybochkov.jaip.model.pdf.validator.PdfFile;
import org.springframework.web.multipart.MultipartFile;

public class Split {

    @FileNotEmpty
    @PdfFile
    private MultipartFile file;
    @Pages
    private String pages;
    private Boolean singleFile;
    private Boolean success;
    private String filename;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public Boolean getSingleFile() {
        return singleFile;
    }

    public void setSingleFile(Boolean singleFile) {
        this.singleFile = singleFile;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String toString() {
        return "Split [file=" + file + ", pages=" + pages + ", singleFile=" + singleFile + "]";
    }
}
