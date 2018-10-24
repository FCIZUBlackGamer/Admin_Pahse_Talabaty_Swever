package com.talabaty.swever.admin.Offers;

import java.io.Serializable;
import java.util.List;

public class TotalOffer implements Serializable{
    int id, ShopId;
    String Name, Photo, Description;
    double Price;
    public boolean Block ;
//    String start_date, end_date;
    List<OperOfferModel> OfferTable;

    public TotalOffer(int ShopId, double price, String Photo, String Description, List<OperOfferModel> offerList) {
        this.ShopId = ShopId;
        this.Price = price;
        this.Photo = Photo;
        this.Description = Description;
        this.OfferTable = offerList;
    }

    public TotalOffer(int id, float price, String start_date, String end_date) {
        this.id = id;
        this.Price = price;
//        this.start_date = start_date;
//        this.end_date = end_date;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public TotalOffer() {
    }

    public void setBlock(boolean block) {
        Block = block;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.Price = price;
    }

    public void setOfferList(List<OperOfferModel> offerList) {
        this.OfferTable = offerList;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public double getPrice() {
        return Price;
    }

    public int getId() {
        return id;
    }

    public String getPhoto() {
        return Photo;
    }

    public int getShopId() {
        return ShopId;
    }

    public List<OperOfferModel> getOfferList() {
        return OfferTable;
    }

    public String getDescription() {
        return Description;
    }
}
