package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.model.news.News;

import java.util.List;

public interface NewsService {

    List<News> getLatest(String slug);
}
