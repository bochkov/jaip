package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.model.cbr.Valute;
import com.sergeybochkov.jaip.model.cbr.ValuteRate;

import java.util.List;

public interface CbrService {

    String VALUTES_URL = "http://www.cbr.ru/scripts/XML_val.asp";
    String DYNAMIC_URL = "http://www.cbr.ru/scripts/XML_dynamic.asp";

    List<Valute> getValutes();

    ValuteRate getValuteRate(String valuteId);

    ValuteRate getPeriodValuteRate(String valuteId);

    ValuteRate getPeriodValuteRate(String valuteId, Integer days);
}
