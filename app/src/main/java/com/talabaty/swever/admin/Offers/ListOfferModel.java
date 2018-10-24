package com.talabaty.swever.admin.Offers;

public class ListOfferModel {
    String name, photo;
    double price;
    int id;

    public ListOfferModel(int id, String name, String photo, double price) {
        this.name = name;
        this.photo = photo;
        this.price = price;
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
