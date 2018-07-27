package com.talabaty.swever.admin.Mabi3at.Delevry.Capital;

public class CapitalModel {
    String id, name, type, amount, price;

    public CapitalModel(String id, String name, String type, String amount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.amount = amount;
    }

    public CapitalModel(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getPrice() {
        return price;
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
