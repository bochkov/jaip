package com.sergeybochkov.jaip.service;

import java.util.List;

import com.sergeybochkov.jaip.model.cbr.Currency;
import com.sergeybochkov.jaip.model.cbr.CurrencyRate;

public interface CbrService {

    String CURRENCIES_URL = "http://www.cbr.ru/scripts/XML_val.asp";
    String DYNAMIC_URL = "http://www.cbr.ru/scripts/XML_dynamic.asp";

    List<Currency> getCurrencies();

    CurrencyRate getCurrencyRate(String currencyId);

    CurrencyRate getPeriodCurrencyRate(String currencyId);

    CurrencyRate getPeriodCurrencyRate(String currencyId, Integer days);
}
