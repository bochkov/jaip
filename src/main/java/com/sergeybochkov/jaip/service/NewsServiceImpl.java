package com.sergeybochkov.jaip.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

import com.google.gson.Gson;
import com.sergeybochkov.jaip.helper.Resource;
import com.sergeybochkov.jaip.model.news.News;
import com.sergeybochkov.jaip.model.news.Source;
import com.sergeybochkov.jaip.model.news.SourceRoot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@Slf4j
@Service
@RequiredArgsConstructor
public final class NewsServiceImpl implements NewsService {

    private static final CompletionService<List<News>> EXECUTOR = new ExecutorCompletionService<>(
            Executors.newFixedThreadPool(5)
    );

    private final Resource resource;

    @Override
    public List<News> getLatest(String slug) {
        List<Future<List<News>>> futures = new ArrayList<>();
        try (InputStream sourceData = this.getClass().getResourceAsStream("/news.json")) {
            if (sourceData == null)
                return Collections.emptyList();
            InputStreamReader in = new InputStreamReader(sourceData);
            SourceRoot sourceRoot = new Gson().fromJson(in, SourceRoot.class);
            Source source = sourceRoot.getSourceBySlug(slug);
            if (source == null)
                return Collections.emptyList();
            else {
                for (String url : source.getUrls()) {
                    futures.add(EXECUTOR.submit(new NewsFetch(url)));
                }
            }
        } catch (IOException ex) {
            LOG.warn(ex.getMessage(), ex);
        }

        List<News> news = new ArrayList<>();
        for (Future<List<News>> future : futures) {
            try {
                news.addAll(future.get());
            } catch (ExecutionException ex) {
                LOG.warn(ex.getMessage(), ex);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        news.sort(Comparator.comparing(News::getPubDate).reversed());
        return news;
    }


    @RequiredArgsConstructor
    private class NewsFetch implements Callable<List<News>> {

        private final String url;

        @Override
        public List<News> call() {
            Document dom = resource.getXml(url);
            if (dom == null)
                return Collections.emptyList();

            Element channel = (Element) dom.getElementsByTagName("channel").item(0);
            String feedTitle = getText(channel, "title");
            String feedLink = getText(channel, "link");
            String feedDescription = getText(channel, "description");

            List<News> news = new ArrayList<>();
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
                news.add(n);
            }
            return news;
        }

        private String getAttr(Element element, String tagName, String attrName) {
            NodeList list = element.getElementsByTagName(tagName);
            if (list.getLength() > 0) {
                Element el = (Element) list.item(0);
                return el.getAttribute(attrName);
            }
            return "";
        }

        private Date getDate(Element element, String tagName) {
            SimpleDateFormat df = new SimpleDateFormat("E, dd MMM y HH:mm:ss Z", Locale.US);
            Date date = null;
            try {
                date = df.parse(getText(element, tagName));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            return date;
        }

        private String getText(Element element, String tagName) {
            NodeList list = element.getElementsByTagName(tagName);
            if (list.getLength() > 0)
                return list.item(0).getTextContent().trim();
            return "";
        }
    }
}
