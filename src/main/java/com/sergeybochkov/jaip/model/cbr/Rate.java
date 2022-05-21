package com.sergeybochkov.jaip.model.cbr;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public final class Rate {

    private Integer nominal;
    private Double currentValue;
    private Date currentDate;
    private Double previousValue;
    private Date previousDate;

}
