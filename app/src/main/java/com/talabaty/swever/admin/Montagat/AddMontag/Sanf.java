package com.talabaty.swever.admin.Montagat.AddMontag;

import java.util.List;

public class Sanf {
    public int Id;
    public String Name ;
    public String BuyPrice ;
    public String SellPrice ;
    public String Summary ;
    public String Description ;
    public String Notes ;
    public String InsertDate ;
    public String EditDate ;
    public String Shop_Id ;
    public String UserId ;
    public String EditUserId ;
    public String CriticalQuantity ;
    public String Amount ;
    public String SampleCatogoriesId ;
    List<Size> Size;
    List<ColorCode> Color;
    List<ImageSource> Galary;


    public Sanf(int id, String name, String buyPrice, String sellPrice, String summary, String description, String notes, String insertDate, String editDate, String shop_Id, String userId, String editUserId, String criticalQuantity, String amount, String sampleCatogoriesId) {
        Id = id;
        Name = name;
        BuyPrice = buyPrice;
        SellPrice = sellPrice;
        Summary = summary;
        Description = description;
        Notes = notes;
        InsertDate = insertDate;
        EditDate = editDate;
        Shop_Id = shop_Id;
        UserId = userId;
        EditUserId = editUserId;
        CriticalQuantity = criticalQuantity;
        Amount = amount;
        SampleCatogoriesId = sampleCatogoriesId;
    }

    public Sanf() {
    }

    public void setGalary(List<ImageSource> galary) {
        Galary = galary;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setBuyPrice(String buyPrice) {
        BuyPrice = buyPrice;
    }

    public void setCriticalQuantity(String criticalQuantity) {
        CriticalQuantity = criticalQuantity;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setEditDate(String editDate) {
        EditDate = editDate;
    }

    public void setEditUserId(String editUserId) {
        EditUserId = editUserId;
    }

    public void setInsertDate(String insertDate) {
        InsertDate = insertDate;
    }

    public void setSampleCatogoriesId(String sampleCatogoriesId) {
        SampleCatogoriesId = sampleCatogoriesId;
    }

    public void setSellPrice(String sellPrice) {
        SellPrice = sellPrice;
    }

    public void setShop_Id(String shop_Id) {
        Shop_Id = shop_Id;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setSizeList(List<Size> sizeList) {
        this.Size = sizeList;
    }

    public void setColorCodes(List<ColorCode> colorCodes) {
        this.Color = colorCodes;
    }

}
