package com.talabaty.swever.admin.Montagat.AddReturanteMontage;

import java.io.Serializable;

public class Size implements Serializable {
    int Id;
    String Size;
    String Price;
    int SampleProductId ;


    public Size() {
    }

    public Size(String size, String price) {
        this.Size = size;
        this.Price = price;
    }

    public Size(int id, String size, int sampleProductId) {
        Size = size;
        Id = id;
        SampleProductId = sampleProductId;
    }

    public void setPrice(String price) {
        this.Price = price;
    }

    public String getPrice() {
        return Price;
    }

    public int getId() {
        return Id;
    }

    public void setSize(String size) {
        Size = size;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setSampleProductId(int sampleProductId) {
        SampleProductId = sampleProductId;
    }

    public String getSize() {
        return Size;
    }
}
