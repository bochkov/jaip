package com.sergeybochkov.jaip.model.pdf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sergeybochkov.jaip.model.pdf.validator.FileNotEmpty;
import com.sergeybochkov.jaip.model.pdf.validator.PdfFile;
import org.springframework.web.multipart.MultipartFile;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Compress {

    @FileNotEmpty
    @PdfFile
    private MultipartFile file;
    private String filename;
    private String message;
    private Double comressRate;
    private Boolean success;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getComressRate() {
        return comressRate;
    }

    public void setComressRate(Double comressRate) {
        this.comressRate = comressRate;
    }
}
