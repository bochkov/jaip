package com.sergeybochkov.jaip.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.sergeybochkov.jaip.model.pdf.Compress;
import com.sergeybochkov.jaip.model.pdf.Merge;
import com.sergeybochkov.jaip.model.pdf.Split;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public final class PdfServiceImpl implements PdfService {

    private static final String TMP_DIR = System.getProperty("java.io.tmpdir") + File.separator;

    @Value("${gsbin}")
    private String gsbin;

    private List<Integer> convert(String pages) {
        List<Integer> result = new ArrayList<>();
        for (String it : pages.split(",")) {
            if (it.contains("-")) {
                String[] spl = it.split("-");
                int start;
                int end;
                if (spl.length == 0) {
                    start = 1;
                    end = 100;
                } else {
                    start = spl[0].isEmpty() ? 1 : Integer.parseInt(spl[0]);
                    end = spl[spl.length - 1].isEmpty() ? 100 : Integer.parseInt(spl[spl.length - 1]);
                }
                for (int i = start; i <= end; ++i)
                    result.add(i);
            } else
                result.add(Integer.parseInt(it));
        }
        return result;
    }

    private String processToFile(MultipartFile file, List<Integer> pages) {
        try {
            String originalName = file.getOriginalFilename();
            if (originalName == null)
                throw new IOException("origin name is null");
            String filename = TMP_DIR + originalName.substring(0, originalName.lastIndexOf(".")) + "-splitted.pdf";
            Document doc = new Document();
            PdfReader sourcePdf = new PdfReader(file.getInputStream());
            OutputStream out = new FileOutputStream(filename);
            PdfWriter writer = PdfWriter.getInstance(doc, out);
            doc.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfImportedPage page;

            for (Integer p : pages) {
                if (p > sourcePdf.getNumberOfPages())
                    break;
                doc.newPage();
                page = writer.getImportedPage(sourcePdf, p);
                cb.addTemplate(page, 0, 0);
            }

            out.flush();
            doc.close();
            out.close();
            return filename;
        } catch (IOException | DocumentException ex) {
            LOG.warn(ex.getMessage(), ex);
            return null;
        }
    }

    private String processToZip(MultipartFile file, List<Integer> pages) {
        try {
            String originalName = file.getOriginalFilename();
            if (originalName == null)
                throw new IOException("origin name is null");
            String filename = TMP_DIR + originalName.substring(0, originalName.lastIndexOf(".")) + "-splitted.zip";
            PdfReader sourcePdf = new PdfReader(file.getInputStream());
            try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(filename))) {
                for (Integer num : pages) {
                    if (num > sourcePdf.getNumberOfPages())
                        break;
                    ZipEntry entry = new ZipEntry(num + ".pdf");
                    out.putNextEntry(entry);

                    Document doc = new Document();
                    PdfWriter writer = PdfWriter.getInstance(doc, out);
                    writer.setCloseStream(false);
                    doc.open();

                    PdfContentByte cb = writer.getDirectContent();
                    PdfImportedPage page = writer.getImportedPage(sourcePdf, num);
                    cb.addTemplate(page, 0, 0);

                    doc.close();
                    out.closeEntry();
                }
            }
            return filename;
        } catch (IOException | DocumentException ex) {
            LOG.warn(ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public Split split(MultipartFile file, String pages, Boolean singleFile) {
        String filename;
        if (Boolean.TRUE.equals(singleFile))
            filename = processToFile(file, convert(pages));
        else
            filename = processToZip(file, convert(pages));

        Split split = new Split();
        split.setSuccess(true);
        split.setFilename(filename);
        return split;
    }

    @Override
    public Merge merge(ArrayList<MultipartFile> files) {
        String filename = TMP_DIR + "result.pdf";
        Merge merge = new Merge();
        try {
            Document doc = new Document();
            FileOutputStream out = new FileOutputStream(filename);
            PdfWriter writer = PdfWriter.getInstance(doc, out);
            doc.open();

            for (MultipartFile file : files) {
                PdfReader reader = new PdfReader(file.getInputStream());
                PdfContentByte cb = writer.getDirectContent();
                for (int i = 1; i <= reader.getNumberOfPages(); ++i) {
                    doc.newPage();
                    PdfImportedPage page = writer.getImportedPage(reader, i);
                    cb.addTemplate(page, 0, 0);
                }
                writer.freeReader(reader);
                reader.close();
            }
            doc.close();
        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
            merge.setSuccess(false);
            return merge;
        }
        merge.setSuccess(true);
        merge.setFilename(filename);
        return merge;
    }

    @Override
    public Compress compress(MultipartFile file) {
        String quiet = "-q";
        String device = "-sDEVICE=pdfwrite";
        String settings = "-dPDFSETTINGS=/ebook";
        String nopause = "-dNOPAUSE";
        String batch = "-dBATCH";
        String originalName = file.getOriginalFilename();
        if (originalName == null)
            return null;
        String outputFile = originalName.substring(0, originalName.lastIndexOf(".")) + "-compressed.pdf";
        String output = "-sOutputFile=" + TMP_DIR + outputFile;

        File origFile = new File(TMP_DIR + file.getOriginalFilename());
        File compFile = new File(TMP_DIR + outputFile);
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(origFile))) {
            byte[] bytes = file.getBytes();
            out.write(bytes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String[] cmd = {gsbin, quiet, device, settings, nopause, batch, output, origFile.getAbsolutePath()};
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        } catch (IOException ex) {
            LOG.warn(ex.getMessage(), ex);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        Compress compress = new Compress();
        double rate = 1 - (double) compFile.length() / origFile.length();
        if (rate >= 0.05) {
            compress.setSuccess(true);
            compress.setFilename(compFile.getPath());
            compress.setCompressRate(rate);
        } else {
            compress.setSuccess(false);
            compress.setMessage("File cannot be compressed");
            compFile.delete();
        }
        origFile.delete();
        return compress;
    }
}
