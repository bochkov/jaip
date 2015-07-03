package com.sergeybochkov.jaip.helper;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.*;

@Component
public class Helper {

    public Document doGetXml(String url, String params) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection conn = new URL(url + "?" + params).openConnection();
            return db.parse(conn.getInputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
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
            ex.printStackTrace();
        }
        return null;
    }

    public <T> T doGetJson(String url, String params, Class<T> cl) {
        Gson gson = new Gson();
        try {
            URLConnection conn = new URL(url + "?" + params).openConnection();
            InputStream response = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
            return gson.fromJson(reader, cl);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
