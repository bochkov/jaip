package com.sergeybochkov.jaip.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sergeybochkov.jaip.helper.Resource;
import com.sergeybochkov.jaip.model.cbr.Currency;
import com.sergeybochkov.jaip.model.cbr.CurrencyRate;
import com.sergeybochkov.jaip.model.cbr.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@Service
@RequiredArgsConstructor
public final class CbrServiceImpl implements CbrService {

    private final Resource resource;

    /**
     * @return список валют
     */
    @Override
    public List<Currency> getCurrencies() {
        int days = 0;
        String params = "d=" + days;
        Document dom = resource.getXml(CURRENCIES_URL, params);
        if (dom == null)
            return Collections.emptyList();

        NodeList items = dom.getElementsByTagName("Item");
        ArrayList<Currency> res = new ArrayList<>(items.getLength());
        for (int i = 0; i < items.getLength(); ++i) {
            Element item = (Element) items.item(i);
            String id = item.getAttribute("ID");
            String name = item.getElementsByTagName("Name").item(0).getTextContent();
            String engName = item.getElementsByTagName("EngName").item(0).getTextContent();
            Integer nominal = Integer.parseInt(item.getElementsByTagName("Nominal").item(0).getTextContent());
            String parentCode = item.getElementsByTagName("ParentCode").item(0).getTextContent().trim();

            res.add(new Currency(id, name, engName, nominal, parentCode));
        }
        return res;
    }

    /**
     * @param currencyId ID валюты
     * @return текущее и предыдущее значение
     */
    @Override
    public CurrencyRate getCurrencyRate(String currencyId) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        String dateReq2 = dateToString(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -20);
        String dateReq1 = dateToString(cal.getTime());

        String params = "VAL_NM_RQ=" + currencyId + "&date_req2=" + dateReq2 + "&date_req1=" + dateReq1;
        Document dom = resource.getXml(DYNAMIC_URL, params);
        if (dom == null)
            return null;

        Element valCurs = (Element) dom.getElementsByTagName("ValCurs").item(0);
        String id = valCurs.getAttribute("ID");

        NodeList records = valCurs.getElementsByTagName("Record");
        Element current = (Element) records.item(records.getLength() - 1);
        Element previous = (Element) records.item(records.getLength() - 2);

        Integer nominal = Integer.parseInt(current.getElementsByTagName("Nominal").item(0).getTextContent());
        String currentValueStr = current.getElementsByTagName("Value").item(0).getTextContent().replace(",", ".");
        Double currentValue = Double.parseDouble(currentValueStr);
        String previousValueStr = previous.getElementsByTagName("Value").item(0).getTextContent().replace(",", ".");
        Double previousValue = Double.parseDouble(previousValueStr);

        Date currentDate = stringToDate(current.getAttribute("Date"));
        Date previousDate = stringToDate(previous.getAttribute("Date"));

        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setId(id);
        Rate rate = new Rate();
        rate.setCurrentValue(currentValue);
        rate.setCurrentDate(currentDate);
        rate.setPreviousValue(previousValue);
        rate.setPreviousDate(previousDate);
        rate.setNominal(nominal);
        currencyRate.setRates(Collections.singletonList(rate));

        return currencyRate;
    }

    @Override
    public CurrencyRate getPeriodCurrencyRate(String currencyId) {
        return getPeriodCurrencyRate(currencyId, 365);
    }

    /**
     * @param currencyId ID валюты
     * @param days     период в днях
     * @return динамика курса валюты за период
     */
    @Override
    public CurrencyRate getPeriodCurrencyRate(String currencyId, Integer days) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        String dateReq2 = dateToString(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -days);
        String dateReq1 = dateToString(cal.getTime());
        String params = "VAL_NM_RQ=" + currencyId + "&date_req2=" + dateReq2 + "&date_req1=" + dateReq1;
        Document dom = resource.getXml(DYNAMIC_URL, params);
        if (dom == null)
            return null;

        NodeList records = dom.getElementsByTagName("Record");
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setId(dom.getDocumentElement().getAttribute("ID"));
        List<Rate> rates = new ArrayList<>(records.getLength());
        for (int i = 0; i < records.getLength(); ++i) {
            Element rec = (Element) records.item(i);
            Date date = stringToDate(rec.getAttribute("Date"));
            Double currentValue = Double.parseDouble(rec.getElementsByTagName("Value").item(0).getTextContent().replace(",", "."));
            Rate rate = new Rate();
            rate.setCurrentDate(date);
            rate.setCurrentValue(currentValue);
            rates.add(rate);
        }
        currencyRate.setRates(rates);
        return currencyRate;
    }

    private String dateToString(Date date) {
        DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        return fmt.format(date);
    }

    private Date stringToDate(String str) {
        DateFormat fmt = new SimpleDateFormat("dd.MM.yyyy");
        fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return fmt.parse(str);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
