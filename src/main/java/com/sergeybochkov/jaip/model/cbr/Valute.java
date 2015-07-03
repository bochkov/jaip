package com.sergeybochkov.jaip.model.cbr;

public class Valute {

    private String id;
    private String name;
    private String engName;
    private Integer nominal;
    private String parentCode;

    public Valute(String id, String name, String engName, Integer nominal, String parentCode) {
        this.id = id;
        this.name = name;
        this.engName = engName;
        this.nominal = nominal;
        this.parentCode = parentCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public Integer getNominal() {
        return nominal;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
