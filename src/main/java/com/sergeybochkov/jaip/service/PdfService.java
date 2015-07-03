package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.model.pdf.Compress;
import com.sergeybochkov.jaip.model.pdf.Merge;
import com.sergeybochkov.jaip.model.pdf.Split;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface PdfService {

    public Split split(MultipartFile file, String pages, Boolean singleFile);

    public Merge merge(ArrayList<MultipartFile> files);

    public Compress compress(MultipartFile file);
}
