package com.sergeybochkov.jaip.service;

import com.google.gson.Gson;
import com.sergeybochkov.jaip.helper.Helper;
import com.sergeybochkov.jaip.model.news.News;
import com.sergeybochkov.jaip.model.news.Source;
import com.sergeybochkov.jaip.model.news.SourceRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private Helper helper;

    private String getText(Element element, String tagName) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list.getLength() > 0)
            return list.item(0).getTextContent().trim();
        return "";
    }

    private String getAttr(Element element, String tagName, String attrName) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list.getLength() > 0) {
            Element el = (Element) list.item(0);
            return el.getAttribute(attrName);
        }
        return "";
    }

    private Date getDate(Element element, String tagName){
        SimpleDateFormat df = new SimpleDateFormat("E, dd MMM y HH:mm:ss Z", Locale.US);
        Date date = null;
        try {
            date = df.parse(getText(element, tagName));
        }
        catch (ParseException ex) { ex.printStackTrace(); }
        return date;
    }

    private ArrayList<News> newsFeeds;

    @Override
    public List<News> getLatest(String slug) {

        newsFeeds = new ArrayList<>();

        Gson gson = new Gson();
        InputStreamReader in = new InputStreamReader(this.getClass().getResourceAsStream("/news.json"));
        SourceRoot sourceRoot = gson.fromJson(in, SourceRoot.class);

        Source source = sourceRoot.getSourceBySlug(slug);
        if (source == null)
            return null;

        ArrayList<Thread> threads = new ArrayList<>(source.getUrls().size());
        for (String url : source.getUrls()) {
            Worker worker = new Worker(url);
            threads.add(new Thread(worker));
        }

        for (Thread t : threads)
            t.start();

        for (Thread t : threads)
            try {
                t.join();
            }
            catch (InterruptedException ex) { ex.printStackTrace(); }

        return newsFeeds;
    }

    private class Worker implements Runnable {

        private String url;

        public Worker(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            Document dom = helper.doGetXml(url, "");
            if (dom == null) return;

            Element channel = (Element) dom.getElementsByTagName("channel").item(0);
            String feedTitle = getText(channel, "title");
            String feedLink = getText(channel, "link");
            String feedDescription = getText(channel, "description");

            NodeList items = channel.getElementsByTagName("item");
            for (int i = 0; i < items.getLength(); ++i) {
                Element el = (Element) items.item(i);
                News n = new News();
                n.setTitle(getText(el, "title"));
                n.setLink(getText(el, "link"));
                n.setPubDate(getDate(el, "pubDate"));
                n.setEnclosure(getAttr(el, "enclosure", "url"));
                n.setDescription(getText(el, "description"));
                n.setFeedTitle(feedTitle);
                n.setFeedLink(feedLink);
                n.setFeedDescription(feedDescription);
                newsFeeds.add(n);
            }
        }
    }
}
