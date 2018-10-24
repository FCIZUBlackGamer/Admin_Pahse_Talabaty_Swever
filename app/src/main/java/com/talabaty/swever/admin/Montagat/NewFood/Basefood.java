package com.talabaty.swever.admin.Montagat.NewFood;

public class Basefood {
    public int Id ;
    public String Name ;
    public float Price ;
    public String Photo ;
    public int ShopId ;

    public Basefood() {
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public void setId(int id) {
        Id = id;
    }
}
