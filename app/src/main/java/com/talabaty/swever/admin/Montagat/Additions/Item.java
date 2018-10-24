package com.talabaty.swever.admin.Montagat.Additions;

public class Item {
    String id, Name;
    float Price;
    int ShopId;

    public Item(String Name, float Price, int ShopId) {
        this.Name = Name;
        this.Price = Price;
        this.ShopId = ShopId;
    }

    public void setPrice(float Price) {
        this.Price = Price;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public float getNum() {
        return Price;
    }
}
