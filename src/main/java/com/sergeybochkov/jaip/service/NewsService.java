package com.sergeybochkov.jaip.service;

import java.util.List;

import com.sergeybochkov.jaip.model.news.News;

public interface NewsService {

    List<News> getLatest(String slug);
}
