package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.model.Cite;

public interface CiteService {

    public static final String FORISMATIC_API_URL = "http://api.forismatic.com/api/1.0/";

    public Cite get();
}
