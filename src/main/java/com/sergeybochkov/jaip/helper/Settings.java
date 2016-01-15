package com.sergeybochkov.jaip.helper;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bochkov")
public class Settings {

    private String defaultCity = "Yekaterinburg,ru";
    private String defaultLang = "ru";
    private String openweatherApiKey;

    public String getDefaultCity() {
        return defaultCity;
    }

    public void setDefaultCity(String defaultCity) {
        this.defaultCity = defaultCity;
    }

    public String getDefaultLang() {
        return defaultLang;
    }

    public void setDefaultLang(String defaultLang) {
        this.defaultLang = defaultLang;
    }

    public String getOpenweatherApiKey() {
        return openweatherApiKey;
    }

    public void setOpenweatherApiKey(String openweatherApiKey) {
        this.openweatherApiKey = openweatherApiKey;
    }
}
