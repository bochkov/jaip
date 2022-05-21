package com.sergeybochkov.jaip.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.sergeybochkov.jaip.model.pdf.Compress;
import com.sergeybochkov.jaip.model.pdf.Merge;
import com.sergeybochkov.jaip.model.pdf.Split;
import com.sergeybochkov.jaip.service.PdfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/pdf")
@RequiredArgsConstructor
public final class PdfController {

    private final PdfService pdfService;

    @RequestMapping("/")
    public String pdf() {
        return "pdf/index";
    }

    @GetMapping(value = "/split/")
    public String split(Model model) {
        model.addAttribute("split", new Split());
        return "pdf/split";
    }

    @PostMapping(value = "/split/")
    public String split(@Valid Split split, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors())
            return "pdf/split";

        Split spl = pdfService.split(split.getFile(), split.getPages(), split.getSingleFile());
        try (InputStream is = new FileInputStream(spl.getFilename())) {
            if (Boolean.TRUE.equals(split.getSingleFile()))
                response.setContentType("application/pdf");
            else
                response.setContentType("application/x-zip-compressed");

            String filename = spl.getFilename().substring(spl.getFilename().lastIndexOf(File.separator) + 1);
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            Files.deleteIfExists(new File(spl.getFilename()).toPath());
        } catch (IOException ex) {
            LOG.warn(ex.getMessage(), ex);
        }
        return "redirect:/pdf/split/";
    }

    @GetMapping(value = "/merge/")
    public String merge(Model model) {
        model.addAttribute("merge", new Merge());
        return "pdf/merge";
    }

    @PostMapping(value = "/merge/")
    public String merge(@Valid Merge merge, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors())
            return "pdf/merge";

        Merge mrg = pdfService.merge(merge.getFiles());
        try (InputStream is = new FileInputStream(mrg.getFilename())) {
            response.setContentType("application/pdf");
            String filename = mrg.getFilename().substring(mrg.getFilename().lastIndexOf(File.separator) + 1);
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            Files.deleteIfExists(new File(mrg.getFilename()).toPath());
        } catch (IOException ex) {
            LOG.warn(ex.getMessage(), ex);
        }
        return "redirect:/pdf/merge/";
    }

    @GetMapping(value = "/compress/")
    public String compress(Model model) {
        model.addAttribute("compress", new Compress());
        return "pdf/compress";
    }

    @PostMapping(value = "/compress/")
    public String compress(@Valid Compress compress, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors())
            return "pdf/compress";

        Compress cmp = pdfService.compress(compress.getFile());
        if (Boolean.TRUE.equals(cmp.getSuccess())) {
            try (InputStream is = new FileInputStream(cmp.getFilename())) {
                response.setContentType("application/pdf");
                String filename = cmp.getFilename().substring(cmp.getFilename().lastIndexOf(File.separator) + 1);
                response.setHeader("Content-Disposition", "attachment; filename=" + filename);
                org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
                Files.deleteIfExists(new File(cmp.getFilename()).toPath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return "pdf/compress";
    }

    @GetMapping(value = "/help/")
    public String help() {
        return "pdf/help";
    }
}
