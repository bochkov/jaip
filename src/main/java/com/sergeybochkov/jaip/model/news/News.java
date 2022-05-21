package com.sergeybochkov.jaip.model.news;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class News {

    private String feedTitle;
    private String feedLink;
    private String feedDescription;

    private String link;
    private String title;
    private String description;
    private Date pubDate;
    private String enclosure;

}
