package com.sergeybochkov.jaip.web.api;

import java.util.List;

import com.sergeybochkov.jaip.model.cbr.Currency;
import com.sergeybochkov.jaip.model.cbr.CurrencyRate;
import com.sergeybochkov.jaip.service.CbrService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cbr")
@RequiredArgsConstructor
public final class ExchangeApi {

    private final CbrService cbrService;

    @GetMapping("/currencies/")
    public List<Currency> currencies() {
        return cbrService.getCurrencies();
    }

    @GetMapping("/rate/{currId}/")
    public CurrencyRate currencyRate(@PathVariable String currId) {
        return cbrService.getCurrencyRate(currId);
    }

    @GetMapping("/rates/{currId}/")
    public CurrencyRate periodCurrencyRate(@PathVariable String currId) {
        return cbrService.getPeriodCurrencyRate(currId);
    }
}
