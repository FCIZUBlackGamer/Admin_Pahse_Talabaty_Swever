package com.talabaty.swever.admin.Montagat;

public class SpinnerModel {
    int id;
    String name;
    float price;

    public SpinnerModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public SpinnerModel(int id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }
}
