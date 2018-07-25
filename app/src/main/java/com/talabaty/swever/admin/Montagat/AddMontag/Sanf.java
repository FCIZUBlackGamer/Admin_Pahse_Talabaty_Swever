package com.talabaty.swever.admin.Montagat.AddMontag;

import java.util.List;

public class Sanf {
    String name;
    String initialamount;
    String desc;
    String buy_price;
    String critical_amount;
    String summary;
    String buyex_price;
    String department;
    String notes;
    String image;
    List<String> size;
    List<String> color_rec;

    public Sanf(String name, String initialamount, String desc, String buy_price, String critical_amount, String summary, String buyex_price, String department, String notes) {
        this.name = name;
        this.initialamount = initialamount;
        this.desc = desc;
        this.buy_price = buy_price;
        this.critical_amount = critical_amount;
        this.summary = summary;
        this.buyex_price = buyex_price;
        this.department = department;
        this.notes = notes;
    }

    public void setBuy_price(String buy_price) {
        this.buy_price = buy_price;
    }

    public void setBuyex_price(String buyex_price) {
        this.buyex_price = buyex_price;
    }

    public void setCritical_amount(String critical_amount) {
        this.critical_amount = critical_amount;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setInitialamount(String initialamount) {
        this.initialamount = initialamount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setColor_rec(List<String> color_rec) {
        this.color_rec = color_rec;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }
}
