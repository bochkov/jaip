package com.sergeybochkov.jaip.helper;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

@Slf4j
@Component
public final class Resource {

    public Document getXml(String url) {
        return getXml(url, "");
    }

    public Document getXml(String url, String params) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            DocumentBuilder db = dbf.newDocumentBuilder();
            String args = params.isEmpty() ? "" : "?" + params;
            URLConnection conn = new URL(url + args).openConnection();
            return db.parse(conn.getInputStream());
        } catch (Exception ex) {
            LOG.warn("url = {}", url, ex);
        }
        return null;
    }

    public <T> T doPostJson(String url, String params, Class<T> cl) {
        Gson gson = new Gson();
        try {
            URLConnection conn = new URL(url).openConnection();
            conn.setDoOutput(true);
            OutputStream out = conn.getOutputStream();
            out.write(params.getBytes());

            InputStream response = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response));

            return gson.fromJson(reader, cl);
        } catch (IOException ex) {
            LOG.warn("url = {}, params = {}", url, params, ex);
        }
        return null;
    }

    public <T> T getJson(String url, String params, Class<T> cl) {
        Gson gson = new Gson();
        try {
            URLConnection conn = new URL(url + "?" + params).openConnection();
            InputStream response = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response, StandardCharsets.UTF_8));
            return gson.fromJson(reader, cl);
        } catch (IOException ex) {
            LOG.warn("url = {}, params = {}", url, params, ex);
        }
        return null;
    }
}
