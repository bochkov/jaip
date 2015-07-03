package com.sergeybochkov.jaip.model.news;

import java.util.ArrayList;

public class SourceRoot {

    private ArrayList<Source> newsSource;

    public ArrayList<Source> getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(ArrayList<Source> newsSource) {
        this.newsSource = newsSource;
    }

    public Source getSourceBySlug(String slug) {
        for (Source source : getNewsSource())
            if (source.getSlug().equals(slug))
                return source;
        return null;
    }
}
