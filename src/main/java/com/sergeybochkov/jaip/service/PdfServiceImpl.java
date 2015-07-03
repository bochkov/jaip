package com.sergeybochkov.jaip.service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.sergeybochkov.jaip.model.pdf.Compress;
import com.sergeybochkov.jaip.model.pdf.Merge;
import com.sergeybochkov.jaip.model.pdf.Split;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class PdfServiceImpl implements PdfService {

    private String tmp = System.getProperty("java.io.tmpdir") + File.separator;
    @Value("${gsbin}")
    private String gsbin;

    private ArrayList<Integer> convert(String pages) {
        ArrayList<Integer> result = new ArrayList<>();
        for (String it : pages.split(",")) {
            if (it.contains("-")) {
                String[] spl = it.split("-");
                int start, end;
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

    private String processToFile(MultipartFile file, ArrayList<Integer> pages) {
        String filename = tmp + file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + "-splitted.pdf";
        Document doc = new Document();
        try {
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
        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
            return null;
        }
        return filename;
    }

    private String processToZip(MultipartFile file, ArrayList<Integer> pages) {
        String filename = tmp + file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + "-splitted.zip";
        try {
            PdfReader sourcePdf = new PdfReader(file.getInputStream());
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(filename));

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
            out.flush();
            out.close();
        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
            return null;
        }
        return filename;
    }

    @Override
    public Split split(MultipartFile file, String pages, Boolean singleFile) {
        String filename;
        if (singleFile)
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
        String filename = tmp + "result.pdf";
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
        String outputFile = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + "-compressed.pdf";
        String output = "-sOutputFile=" + tmp + outputFile;

        File origFile = new File(tmp + file.getOriginalFilename());
        File compFile = new File(tmp + outputFile);
        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(origFile));
            out.write(bytes);
            out.close();

            String[] cmd = {gsbin, quiet, device, settings, nopause, batch, output, origFile.getAbsolutePath()};
            Process p = Runtime.getRuntime().exec(cmd);
            try {
                p.waitFor();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Compress compress = new Compress();
        Double rate = 1 - (double) compFile.length() / origFile.length();
        if (rate >= 0.05) {
            compress.setSuccess(true);
            compress.setFilename(compFile.getPath());
            compress.setComressRate(rate);
        } else {
            compress.setSuccess(false);
            compress.setMessage("File cannot be compressed");
            compFile.delete();
        }
        origFile.delete();
        return compress;
    }
}
