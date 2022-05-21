package com.sergeybochkov.jaip.model.cbr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Currency {

    private String id;
    private String name;
    private String engName;
    private Integer nominal;
    private String parentCode;

}
