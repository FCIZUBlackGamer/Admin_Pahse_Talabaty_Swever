package com.talabaty.swever.admin.Montagat.AddMontag;

import java.io.Serializable;

public class ColorCode implements Serializable{
//    int color;

    int Id ;
    String Color ;
    int Amount ;
    int SampleProductId ;


    public ColorCode(String color) {
        this.Color = color;
    }

    public ColorCode(int id, String color, int amount, int sampleProductId) {
        Id = id;
        Color = color;
        Amount = amount;
        SampleProductId = sampleProductId;
    }

//    public int getColor() {
//        return color;
//    }

    public void setSampleProductId(int sampleProductId) {
        SampleProductId = sampleProductId;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getColor() {
        return Color;
    }
}
