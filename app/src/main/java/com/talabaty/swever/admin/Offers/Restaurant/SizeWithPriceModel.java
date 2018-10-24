package com.talabaty.swever.admin.Offers.Restaurant;

public class SizeWithPriceModel {
    int id, SampleProductId;
    String size;
    double price;

    public SizeWithPriceModel(int id, int sampleProductId, String size, double price) {
        this.id = id;
        SampleProductId = sampleProductId;
        this.size = size;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getSampleProductId() {
        return SampleProductId;
    }

    public String getSize() {
        return size;
    }
}
