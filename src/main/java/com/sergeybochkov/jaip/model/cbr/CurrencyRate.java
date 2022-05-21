package com.sergeybochkov.jaip.model.cbr;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public final class CurrencyRate {

    private String id;
    private List<Rate> rates;

}
