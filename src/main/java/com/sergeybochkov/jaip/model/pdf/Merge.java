package com.sergeybochkov.jaip.model.pdf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sergeybochkov.jaip.model.pdf.validator.FileNotEmpty;
import com.sergeybochkov.jaip.model.pdf.validator.PdfFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Merge {

    @FileNotEmpty
    @PdfFile
    private ArrayList<MultipartFile> files;
    private String filename;
    private Boolean success;

    public ArrayList<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<MultipartFile> files) {
        this.files = files;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
