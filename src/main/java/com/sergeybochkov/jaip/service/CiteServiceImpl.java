package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.helper.Resource;
import com.sergeybochkov.jaip.model.Cite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class CiteServiceImpl implements CiteService {

    private final Resource resource;

    @Override
    public Cite get() {
        String method = "getQuote";
        String format = "json";
        String query = "method=" + method + "&format=" + format + "&lang=ru";
        return resource.getJson(FORISMATIC_API_URL, query, Cite.class);
    }
}
