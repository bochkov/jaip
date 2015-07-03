package com.sergeybochkov.jaip.model.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

public class ForecastHorly {

    private String cod;
    private Double message;
    private City city;
    private Integer cnt;
    private ArrayList<L> list;

    private class City {
        private Integer id;
        private String name;
        private Coord coord;
        private String country;
        private Integer population;
        private Sys sys;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Coord getCoord() {
            return coord;
        }

        public void setCoord(Coord coord) {
            this.coord = coord;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Integer getPopulation() {
            return population;
        }

        public void setPopulation(Integer population) {
            this.population = population;
        }

        public Sys getSys() {
            return sys;
        }

        public void setSys(Sys sys) {
            this.sys = sys;
        }
    }

    private class Coord {
        private Double lon;
        private Double lat;

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }
    }

    private class Sys {
        private Integer population;
        private String pod;

        public Integer getPopulation() {
            return population;
        }

        public void setPopulation(Integer population) {
            this.population = population;
        }

        public String getPod() {
            return pod;
        }

        public void setPod(String pod) {
            this.pod = pod;
        }
    }

    private class L {
        private Long dt;
        private Main main;
        private ArrayList<W> weather;
        private Clouds clouds;
        private Wind wind;
        private Snow snow;
        private Rain rain;
        private Sys sys;
        private String dt_txt;

        public Long getDt() {
            return dt;
        }

        public void setDt(Long dt) {
            this.dt = dt;
        }

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public ArrayList<W> getWeather() {
            return weather;
        }

        public void setWeather(ArrayList<W> weather) {
            this.weather = weather;
        }

        public Clouds getClouds() {
            return clouds;
        }

        public void setClouds(Clouds clouds) {
            this.clouds = clouds;
        }

        public Wind getWind() {
            return wind;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public Snow getSnow() {
            return snow;
        }

        public void setSnow(Snow snow) {
            this.snow = snow;
        }

        public Rain getRain() {
            return rain;
        }

        public void setRain(Rain rain) {
            this.rain = rain;
        }

        public Sys getSys() {
            return sys;
        }

        public void setSys(Sys sys) {
            this.sys = sys;
        }

        public String getDt_txt() {
            return dt_txt;
        }

        public void setDt_txt(String dt_txt) {
            this.dt_txt = dt_txt;
        }
    }

    private class Main {
        private Double temp;
        private Double temp_min;
        private Double temp_max;
        private Double pressure;
        private Double sea_level;
        private Double grnd_level;
        private Integer humidity;
        private Double temp_kf;

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        public Double getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(Double temp_min) {
            this.temp_min = temp_min;
        }

        public Double getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(Double temp_max) {
            this.temp_max = temp_max;
        }

        public Double getPressure() {
            return pressure;
        }

        public void setPressure(Double pressure) {
            this.pressure = pressure;
        }

        public Double getSea_level() {
            return sea_level;
        }

        public void setSea_level(Double sea_level) {
            this.sea_level = sea_level;
        }

        public Double getGrnd_level() {
            return grnd_level;
        }

        public void setGrnd_level(Double grnd_level) {
            this.grnd_level = grnd_level;
        }

        public Integer getHumidity() {
            return humidity;
        }

        public void setHumidity(Integer humidity) {
            this.humidity = humidity;
        }

        public Double getTemp_kf() {
            return temp_kf;
        }

        public void setTemp_kf(Double temp_kf) {
            this.temp_kf = temp_kf;
        }
    }

    private class W {
        private Integer id;
        private String main;
        private String description;
        private String icon;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    private class Clouds {
        private Integer all;

        public Integer getAll() {
            return all;
        }

        public void setAll(Integer all) {
            this.all = all;
        }
    }

    private class Wind {
        private Double speed;
        private Double deg;

        public Double getSpeed() {
            return speed;
        }

        public void setSpeed(Double speed) {
            this.speed = speed;
        }

        public Double getDeg() {
            return deg;
        }

        public void setDeg(Double deg) {
            this.deg = deg;
        }
    }

    private class Snow {
        @JsonProperty("3h")
        private Double threeh;

        public Double getThreeh() {
            return threeh;
        }

        public void setThreeh(Double threeh) {
            this.threeh = threeh;
        }
    }

    private class Rain {
        @JsonProperty("3h")
        private Double threeh;

        public Double getThreeh() {
            return threeh;
        }

        public void setThreeh(Double threeh) {
            this.threeh = threeh;
        }
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public ArrayList<L> getList() {
        return list;
    }

    public void setList(ArrayList<L> list) {
        this.list = list;
    }
}
