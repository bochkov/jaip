package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.model.cbr.Valute;
import com.sergeybochkov.jaip.model.cbr.ValuteRate;

import java.util.List;

public interface CbrService {

    public static final String VALUTES_URL = "http://www.cbr.ru/scripts/XML_val.asp";
    public static final String DYNAMIC_URL = "http://www.cbr.ru/scripts/XML_dynamic.asp";

    public List<Valute> getValutes();

    public ValuteRate getValuteRate(String valuteId);

    public ValuteRate getPeriodValuteRate(String valuteId);

    public ValuteRate getPeriodValuteRate(String valuteId, Integer days);
}
