package com.sergeybochkov.jaip.model.forecast;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class ForecastHourly {

    private String cod;
    private Double message;
    private City city;
    private Integer cnt;
    private ArrayList<L> list;

    @Data
    @NoArgsConstructor
    private static final class City {
        private Integer id;
        private String name;
        private Coord coord;
        private String country;
        private Integer population;
        private Sys sys;
    }

    @Data
    @NoArgsConstructor
    private static final class Coord {
        private Double lon;
        private Double lat;
    }

    @Data
    @NoArgsConstructor
    private static final class Sys {
        private Integer population;
        private String pod;
    }

    @Data
    @NoArgsConstructor
    private static final class L {
        private Long dt;
        private Main main;
        private ArrayList<W> weather;
        private Clouds clouds;
        private Wind wind;
        private Snow snow;
        private Rain rain;
        private Sys sys;
        @JsonProperty("dt_txt")
        private String dtTxt;
    }

    @Data
    @NoArgsConstructor
    private static final class Main {
        private Double temp;
        @JsonProperty("temp_min")
        private Double tempMin;
        @JsonProperty("temp_max")
        private Double tempMax;
        private Double pressure;
        @JsonProperty("sea_level")
        private Double seaLevel;
        @JsonProperty("grnd_level")
        private Double grndLevel;
        private Integer humidity;
        @JsonProperty("temp_kf")
        private Double tempKf;
    }

    @Data
    @NoArgsConstructor
    private static final class W {
        private Integer id;
        private String main;
        private String description;
        private String icon;
    }

    @Data
    @NoArgsConstructor
    private static final class Clouds {
        private Integer all;
    }

    @Data
    @NoArgsConstructor
    private static final class Wind {
        private Double speed;
        private Double deg;
    }

    @Data
    @NoArgsConstructor
    private static final class Snow {
        @JsonProperty("3h")
        private Double threeh;
    }

    @Data
    @NoArgsConstructor
    private static final class Rain {
        @JsonProperty("3h")
        private Double threeh;
    }
}
