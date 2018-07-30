package com.talabaty.swever.admin.Montagat.ControlMontag;

import com.talabaty.swever.admin.Montagat.AddMontag.ColorCode;
import com.talabaty.swever.admin.Montagat.AddMontag.ImageSource;
import com.talabaty.swever.admin.Montagat.AddMontag.Size;

import java.io.Serializable;
import java.util.List;

public class ControlMontagModel implements Serializable{
    int Id;
    int num;
    String Name ;
    int BuyPrice ;
    int SellPrice ;
    String Summary ;
    String Description ;
    String Notes ;
    String InsertDate ;
    String InsertTime ;
    int Shop_Id ;
    int UserId ;
    int EditUserId ;
    int CriticalQuantity ;
    int Amount ;
    int SampleCatogoriesId ;
    List<Size> Size;
    List<ColorCode> Color;
    List<ImageSource> Gallary;


//    public Sanf(int id, String name, String buyPrice, String sellPrice, String summary, String description, String notes, String insertDate, String editDate, String shop_Id, String userId, String editUserId, String criticalQuantity, String amount, String sampleCatogoriesId) {
//        Id = id;
//        Name = name;
//        BuyPrice = buyPrice;
//        SellPrice = sellPrice;
//        Summary = summary;
//        Description = description;
//        Notes = notes;
//        InsertDate = insertDate;
//        EditDate = editDate;
//        Shop_Id = shop_Id;
//        UserId = userId;
//        EditUserId = editUserId;
//        CriticalQuantity = criticalQuantity;
//        Amount = amount;
//        SampleCatogoriesId = sampleCatogoriesId;
//    }


    public ControlMontagModel() {
    }

    public ControlMontagModel(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setBuyPrice(int buyPrice) {
        BuyPrice = buyPrice;
    }

    public void setSellPrice(int sellPrice) {
        SellPrice = sellPrice;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public void setInsertDate(String insertDate) {
        InsertDate = insertDate;
    }

    public void setInsertTime(String InsertTime) {
        this.InsertTime = InsertTime;
    }

    public void setShop_Id(int shop_Id) {
        Shop_Id = shop_Id;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setEditUserId(int editUserId) {
        EditUserId = editUserId;
    }

    public void setCriticalQuantity(int criticalQuantity) {
        CriticalQuantity = criticalQuantity;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public void setSampleCatogoriesId(int sampleCatogoriesId) {
        SampleCatogoriesId = sampleCatogoriesId;
    }

    public void setSize(List<com.talabaty.swever.admin.Montagat.AddMontag.Size> size) {
        Size = size;
    }

    public void setColor(List<ColorCode> color) {
        Color = color;
    }

    public void setGallary(List<ImageSource> gallary) {
        Gallary = gallary;
    }

    public String getName() {
        return Name;
    }

    public int getId() {
        return Id;
    }

    public String getDescription() {
        return Description;
    }

    public int getAmount() {
        return Amount;
    }

    public int getBuyPrice() {
        return BuyPrice;
    }

    public int getCriticalQuantity() {
        return CriticalQuantity;
    }

    public int getEditUserId() {
        return EditUserId;
    }

    public int getSellPrice() {
        return SellPrice;
    }

    public int getSampleCatogoriesId() {
        return SampleCatogoriesId;
    }

    public int getShop_Id() {
        return Shop_Id;
    }

    public int getUserId() {
        return UserId;
    }

    public List<ColorCode> getColor() {
        return Color;
    }

    public List<com.talabaty.swever.admin.Montagat.AddMontag.Size> getSize() {
        return Size;
    }

    public List<ImageSource> getGallary() {
        return Gallary;
    }

    public String getInsertTime() {
        return InsertTime;
    }

    public String getInsertDate() {
        return InsertDate;
    }

    public String getNotes() {
        return Notes;
    }

    public String getSummary() {
        return Summary;
    }
}
