package com.talabaty.swever.admin.Mabi3at.DoneTalabat;

public class Talabat {
    String id, name, phone, num, estlam_time, estlam_date, address, tasleem_time, tasleem_date;
    String total, reason;
    String amount, price, emergency_amount;
    String duration, job_title, manage, place_name, time;

    public Talabat(String id, String name, String phone, String num, String estlam_time, String estlam_date, String address, String tasleem_time, String tasleem_date) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.num = num;
        this.estlam_time = estlam_time;
        this.estlam_date = estlam_date;
        this.address = address;
        this.tasleem_time = tasleem_time;
        this.tasleem_date = tasleem_date;
    }

    public Talabat(int x, String id, String name, String phone, String total, String estlam_time, String estlam_date, String address, String tasleem_time, String tasleem_date) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.total = total;
        this.estlam_time = estlam_time;
        this.estlam_date = estlam_date;
        this.address = address;
        this.tasleem_time = tasleem_time;
        this.tasleem_date = tasleem_date;
    }

    public Talabat(int x, String id, String num, String name, String estlam_time, String estlam_date, String amount, String price, String emergency_amount) {
        this.id = id;
        this.name = name;
        this.num = num;
        this.amount = amount;
        this.estlam_time = estlam_time;
        this.estlam_date = estlam_date;
        this.price = price;
        this.emergency_amount = emergency_amount;
    }

    public Talabat(int x, String id, String name, String time, String job_title, String duration, String place_name, String manage) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.job_title = job_title;
        this.duration = duration;
        this.place_name = place_name;
        this.manage = manage;
    }

    public Talabat(String id, String name, String date, String num, String estlam_time, String estlam_date, String address, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.num = num;
        this.estlam_time = estlam_time;
        this.estlam_date = estlam_date;
        this.address = address;
    }

    public Talabat(String id, String name, String total, String reason, String estlam_time, String estlam_date) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.reason = reason;
        this.estlam_time = estlam_time;
        this.estlam_date = estlam_date;
    }

    public Talabat(String id, String name, String total, String amount, String price) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.amount = amount;
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEstlam_date() {
        return estlam_date;
    }

    public String getEstlam_time() {
        return estlam_time;
    }

    public String getTasleem_date() {
        return tasleem_date;
    }

    public String getTasleem_time() {
        return tasleem_time;
    }

    public String getReason() {
        return reason;
    }

    public String getTotal() {
        return total;
    }

    public String getPrice() {
        return price;
    }

    public String getAmount() {
        return amount;
    }

    public String getEmergency_amount() {
        return emergency_amount;
    }

    public String getDuration() {
        return duration;
    }

    public String getJob_title() {
        return job_title;
    }

    public String getManage() {
        return manage;
    }

    public String getPlace_name() {
        return place_name;
    }

    public String getTime() {
        return time;
    }
}
