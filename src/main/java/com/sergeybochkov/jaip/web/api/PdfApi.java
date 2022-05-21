package com.sergeybochkov.jaip.web.api;

import javax.validation.Valid;

import com.sergeybochkov.jaip.model.pdf.Compress;
import com.sergeybochkov.jaip.model.pdf.Merge;
import com.sergeybochkov.jaip.model.pdf.Split;
import com.sergeybochkov.jaip.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pdf")
@RequiredArgsConstructor
public final class PdfApi {

    private final PdfService pdfService;

    // curl http://127.0.0.1:8080/pdf/split/ -F "file=@...pdf" -F "pages=1,3" -F "singleFile=true"
    @PostMapping(value = "/split/")
    public Split split(@Valid Split split) {
        return pdfService.split(split.getFile(), split.getPages(), split.getSingleFile());
    }

    // curl http://127.0.0.1:8080/pdf/merge/ -F "files=@1.pdf" -F "files=@3.pdf"
    @PostMapping(value = "/merge/")
    public Merge merge(@Valid Merge merge) {
        return pdfService.merge(merge.getFiles());
    }

    // curl http://127.0.0.1:8080/pdf/compress/ -F "file=@20141107.pdf"
    @PostMapping(value = "/compress/")
    public Compress compress(@Valid Compress compress) {
        return pdfService.compress(compress.getFile());
    }
}
