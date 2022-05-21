package com.sergeybochkov.jaip.model.forecast;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class ForecastDaily {

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
    }

    @Data
    @NoArgsConstructor
    private static final class L {
        private Long dt;
        private Temp temp;
        private Double pressure;
        private Integer humidity;
        private ArrayList<W> weather;
        private Double speed;
        private Integer deg;
        private Integer clouds;
        private Double snow;
        private Double rain;
    }

    @Data
    @AllArgsConstructor
    private static final class Temp {
        private Double min;
        private Double max;
        private Double morn;
        private Double day;
        private Double eve;
        private Double night;
    }

    @Data
    @AllArgsConstructor
    private static final class W {
        private Integer id;
        private String main;
        private String description;
        private String icon;
    }
}
