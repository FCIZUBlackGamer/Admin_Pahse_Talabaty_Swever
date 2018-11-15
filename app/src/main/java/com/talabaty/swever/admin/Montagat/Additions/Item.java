package com.talabaty.swever.admin.Montagat.Additions;

public class Item {
    String id, Name;
    float Price;
    int ShopId, num;
    int Shop_Id;

    public void setId(String id) {
        this.id = id;
    }

    public void setShop_Id(int shop_Id) {
        Shop_Id = shop_Id;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public Item(int Num) {
        this.num = Num;
    }

    public Item(String Name, float Price, int ShopId) {
        this.Name = Name;
        this.Price = Price;
        this.ShopId = ShopId;
        this.Shop_Id = ShopId;
    }

    public Item(String Name, int ShopId) {
        this.Name = Name;
        this.ShopId = ShopId;
        this.Shop_Id = ShopId;
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

    public int getNum() {
        return num;
    }

    public float getPrice() {
        return Price;
    }
}
