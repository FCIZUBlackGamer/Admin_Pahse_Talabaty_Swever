package com.talabaty.swever.admin.Montagat.AddMontag;

public class ColorCode {
    int color;

    public int Id ;
    public String Color ;
    public int Amount ;
    public int SampleProductId ;


    public ColorCode(int color) {
        this.color = color;
    }

    public ColorCode(int id, String color, int amount, int sampleProductId) {
        Id = id;
        Color = color;
        Amount = amount;
        SampleProductId = sampleProductId;
    }

    public int getColor() {
        return color;
    }

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
}
