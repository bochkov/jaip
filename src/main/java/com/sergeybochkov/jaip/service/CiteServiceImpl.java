package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.helper.Helper;
import com.sergeybochkov.jaip.helper.Settings;
import com.sergeybochkov.jaip.model.Cite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CiteServiceImpl implements CiteService {

    @Autowired
    private Helper helper;

    @Override
    public Cite get() {
        String method = "getQuote";
        String format = "json";
        String query = "method=" + method + "&format=" + format + "&lang=" + Settings.DEFAULT_LANG;
        return helper.doGetJson(FORISMATIC_API_URL, query, Cite.class);
    }
}
