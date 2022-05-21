package com.sergeybochkov.jaip.web.api;

import java.util.List;

import com.sergeybochkov.jaip.model.news.News;
import com.sergeybochkov.jaip.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public final class NewsApi {

    private final NewsService newsService;

    @GetMapping("/{slug}/")
    public List<News> news(@PathVariable String slug) {
        return newsService.getLatest(slug);
    }
}
