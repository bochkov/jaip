package com.sergeybochkov.jaip.model.news;

import java.util.ArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class SourceRoot {

    private ArrayList<Source> newsSource;

    public Source getSourceBySlug(String slug) {
        for (Source source : getNewsSource())
            if (source.getSlug().equals(slug))
                return source;
        return null;
    }
}
