package com.sergeybochkov.jaip.service;

import com.sergeybochkov.jaip.helper.Helper;
import com.sergeybochkov.jaip.model.cbr.Rate;
import com.sergeybochkov.jaip.model.cbr.Valute;
import com.sergeybochkov.jaip.model.cbr.ValuteRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CbrServiceImpl implements CbrService {

    @Autowired
    private Helper helper;

    /**
     * @return список валют
     */
    public ArrayList<Valute> getValutes() {
        Integer days = 0;
        String params = "d=" + days;
        Document dom = helper.doGetXml(VALUTES_URL, params);
        if (dom == null) return null;

        NodeList items = dom.getElementsByTagName("Item");
        ArrayList<Valute> res = new ArrayList<>(items.getLength());
        for (int i = 0; i < items.getLength(); ++i) {
            Element item = (Element) items.item(i);
            String id = item.getAttribute("ID");
            String name = item.getElementsByTagName("Name").item(0).getTextContent();
            String engName = item.getElementsByTagName("EngName").item(0).getTextContent();
            Integer nominal = Integer.parseInt(item.getElementsByTagName("Nominal").item(0).getTextContent());
            String parentCode = item.getElementsByTagName("ParentCode").item(0).getTextContent().trim();

            res.add(new Valute(id, name, engName, nominal, parentCode));
        }
        return res;
    }

    /**
     * @param valuteId ID валюты
     * @return текущее и предыдущее значение
     */
    public ValuteRate getValuteRate(String valuteId) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        String dateReq2 = dateToString(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -20);
        String dateReq1 = dateToString(cal.getTime());

        String params = "VAL_NM_RQ=" + valuteId + "&date_req2=" + dateReq2 + "&date_req1=" + dateReq1;
        Document dom = helper.doGetXml(DYNAMIC_URL, params);
        if (dom == null) return null;

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

        ValuteRate valuteRate = new ValuteRate();
        valuteRate.setId(id);
        Rate rate = new Rate();
        rate.setCurrentValue(currentValue);
        rate.setCurrentDate(currentDate);
        rate.setPreviousValue(previousValue);
        rate.setPreviousDate(previousDate);
        rate.setNominal(nominal);
        valuteRate.setRates(Arrays.asList(rate));

        return valuteRate;
    }

    public ValuteRate getPeriodValuteRate(String valuteId) {
        return getPeriodValuteRate(valuteId, 365);
    }

    /**
     * @param valuteId ID валюты
     * @param days     период в днях
     * @return динамика курса валюты за период
     */
    public ValuteRate getPeriodValuteRate(String valuteId, Integer days) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        String dateReq2 = dateToString(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -days);
        String dateReq1 = dateToString(cal.getTime());
        String params = "VAL_NM_RQ=" + valuteId + "&date_req2=" + dateReq2 + "&date_req1=" + dateReq1;
        Document dom = helper.doGetXml(DYNAMIC_URL, params);
        if (dom == null) return null;

        NodeList records = dom.getElementsByTagName("Record");
        ValuteRate valuteRate = new ValuteRate();
        valuteRate.setId(dom.getDocumentElement().getAttribute("ID"));
        List<Rate> rates = new ArrayList<>(records.getLength());
        for (int i = 0; i < records.getLength(); ++i) {
            Element record = (Element) records.item(i);
            Date date = stringToDate(record.getAttribute("Date"));
            Double currentValue = Double.parseDouble(record.getElementsByTagName("Value").item(0).getTextContent().replace(",", "."));
            Rate rate = new Rate();
            rate.setCurrentDate(date);
            rate.setCurrentValue(currentValue);
            rates.add(rate);
        }
        valuteRate.setRates(rates);
        return valuteRate;
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
