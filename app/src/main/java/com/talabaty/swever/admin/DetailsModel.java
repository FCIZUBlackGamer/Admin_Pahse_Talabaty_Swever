package com.talabaty.swever.admin;

public class DetailsModel {
    String id, name, type, amount;

    public DetailsModel(String id, String name, String type, String amount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
