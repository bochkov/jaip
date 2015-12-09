package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.model.pdf.Compress;
import com.sergeybochkov.jaip.model.pdf.Merge;
import com.sergeybochkov.jaip.model.pdf.Split;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface PdfService {

    Split split(MultipartFile file, String pages, Boolean singleFile);

    Merge merge(ArrayList<MultipartFile> files);

    Compress compress(MultipartFile file);

}