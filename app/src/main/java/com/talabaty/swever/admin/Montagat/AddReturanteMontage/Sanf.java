package com.talabaty.swever.admin.Montagat.AddReturanteMontage;

import java.util.List;

public class Sanf {
    public int Id;
    public String Name ;
    public int BuyPrice ;
    public int SellPrice ;
    public String Summary ;
    public String Description ;
    public String Notes ;
    public String InsertDate ;
    public String EditDate ;
    public int Shop_Id ;
    public int UserId ;
    public int EditUserId ;
    public int CriticalQuantity ;
    public int Amount ;
    public int SampleCatogoriesId ;
    List<Size> Size;
    List<ImageSource> Gallary;

    public Sanf() {
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

    public void setEditDate(String editDate) {
        EditDate = editDate;
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

    public void setSize(List<Size> size) {
        Size = size;
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

    public List<Size> getSize() {
        return Size;
    }

    public List<ImageSource> getGallary() {
        return Gallary;
    }

    public String getEditDate() {
        return EditDate;
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
