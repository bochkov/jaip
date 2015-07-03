package com.sergeybochkov.jaip.web;

import com.sergeybochkov.jaip.model.pdf.Compress;
import com.sergeybochkov.jaip.model.pdf.Merge;
import com.sergeybochkov.jaip.model.pdf.Split;
import com.sergeybochkov.jaip.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @RequestMapping("/")
    public String pdf() {
        return "pdf/index";
    }

    @RequestMapping(value = "/split/", method = RequestMethod.GET)
    public String split(Split split) {
        return "pdf/split";
    }

    @RequestMapping(value = "/split/", method = RequestMethod.POST)
    public String split(@Valid Split split, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors())
            return "pdf/split";

        try {
            Split spl = pdfService.split(split.getFile(), split.getPages(), split.getSingleFile());
            InputStream is = new FileInputStream(spl.getFilename());
            if (split.getSingleFile())
                response.setContentType("application/pdf");
            else
                response.setContentType("application/x-zip-compressed");

            String filename = spl.getFilename().substring(spl.getFilename().lastIndexOf(File.separator) + 1);
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            is.close();
            new File(spl.getFilename()).delete();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "redirect:/pdf/split/";
    }

    @RequestMapping(value = "/merge/", method = RequestMethod.GET)
    public String merge(Merge merge) {
        return "pdf/merge";
    }

    @RequestMapping(value = "/merge/", method = RequestMethod.POST)
    public String merge(@Valid Merge merge, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors())
            return "pdf/merge";

        try {
            Merge mrg = pdfService.merge(merge.getFiles());
            InputStream is = new FileInputStream(mrg.getFilename());
            response.setContentType("application/pdf");
            String filename = mrg.getFilename().substring(mrg.getFilename().lastIndexOf(File.separator) + 1);
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            is.close();
            new File(mrg.getFilename()).delete();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "redirect:/pdf/merge/";
    }

    @RequestMapping(value = "/compress/", method = RequestMethod.GET)
    public String compress(Compress compress) {
        return "pdf/compress";
    }

    @RequestMapping(value = "/compress/", method = RequestMethod.POST)
    public String compress(@Valid Compress compress, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors())
            return "pdf/compress";

        try {
            Compress cmp = pdfService.compress(compress.getFile());
            if (cmp.getSuccess()) {
                InputStream is = new FileInputStream(cmp.getFilename());
                response.setContentType("application/pdf");
                String filename = cmp.getFilename().substring(cmp.getFilename().lastIndexOf(File.separator) + 1);
                response.setHeader("Content-Disposition", "attachment; filename=" + filename);
                org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
                is.close();
                new File(cmp.getFilename()).delete();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "pdf/compress";
    }

    @RequestMapping(value = "/help/")
    public String help() {
        return "pdf/help";
    }
}
