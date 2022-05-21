package com.sergeybochkov.jaip.web.api;

import com.sergeybochkov.jaip.model.Cite;
import com.sergeybochkov.jaip.service.CiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forismatic/")
@RequiredArgsConstructor
public final class CiteApi {

    private final CiteService citeService;

    @GetMapping
    public Cite forismatic() {
        return citeService.get();
    }
}
