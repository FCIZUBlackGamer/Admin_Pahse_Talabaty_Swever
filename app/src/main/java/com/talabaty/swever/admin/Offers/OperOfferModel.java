package com.talabaty.swever.admin.Offers;

import java.io.Serializable;

public class OperOfferModel implements Serializable{
    public int Id ;
    public int OffersId ;
    public int SampleProductId ;
    public int Amount ;
    public int SizeId ;
    double price;
    String name;
    String size_name;
    String UnitsName;

    public OperOfferModel() {
    }

    public OperOfferModel(int id, String name, int amount, double price) {
        Id = id;
        Amount = amount;
        this.price = price;
        this.name = name;
    }

    public void setUnitsName(String unitsName) {
        UnitsName = unitsName;
    }

    public String getUnitsName() {
        return UnitsName;
    }

    public String getSize_name() {
        return size_name;
    }

    public OperOfferModel(int sampleProductId, double price, int num, String name) {
        SampleProductId = sampleProductId;
        this.price = price;
        this.name = name;
        this.Amount = num;
    }

    public OperOfferModel(int sampleProductId, double price, int num, int SizeId, String name) {
        SampleProductId = sampleProductId;
        this.price = price;
        this.name = name;
        this.Amount = num;
        this.SizeId = SizeId;
    }


    public OperOfferModel(int sampleProductId, double price, String name) {
        SampleProductId = sampleProductId;
        this.price = price;
        this.name = name;
    }

    public void setSize_name(String size_name) {
        this.size_name = size_name;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public void setSampleProductId(int sampleProductId) {
        SampleProductId = sampleProductId;
    }

    public void setOffersId(int offersId) {
        OffersId = offersId;
    }

    public void setSizeId(int sizeId) {
        SizeId = sizeId;
    }

    public int getId() {
        return Id;
    }

    public float getAmount() {
        return Amount;
    }

    public double getPrice() {
        return price;
    }

    public int getOffersId() {
        return OffersId;
    }

    public int getSampleProductId() {
        return SampleProductId;
    }

    public int getSizeId() {
        return SizeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
