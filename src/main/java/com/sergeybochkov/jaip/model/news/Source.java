package com.sergeybochkov.jaip.model.news;

import java.util.ArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class Source {

    private String description;
    private String slug;
    private ArrayList<String> urls;

}