package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.model.Cite;

public interface CiteService {

    String FORISMATIC_API_URL = "https://api.forismatic.com/api/1.0/";

    Cite get();
}
