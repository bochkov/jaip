package com.sergeybochkov.jaip.model.forecast;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class Weather {

    private Coord coord;
    private Sys sys;
    private ArrayList<W> weather;
    private String base;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private Long dt;
    private Integer id;
    private String name;
    private Integer cod;
    private String message;

    @Data
    @NoArgsConstructor
    private static final class Coord {
        private Double lon;
        private Double lat;
    }

    @Data
    @NoArgsConstructor
    private static final class Sys {
        private Integer type;
        private Integer id;
        private Double message;
        private String country;
        private Long sunrise;
        private Long sunset;
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
    private static final class Main {
        private Double temp;
        private Double pressure;
        private Integer humidity;
        @SerializedName("temp_min")
        @JsonProperty("temp_min")
        private Double tempMin;
        @SerializedName("temp_max")
        @JsonProperty("temp_max")
        private Double tempMax;
    }

    @Data
    @NoArgsConstructor
    private static final class Wind {
        private Double speed;
        private Double deg;
    }

    @Data
    @NoArgsConstructor
    private static final class Clouds {
        private Integer all;
    }
}
