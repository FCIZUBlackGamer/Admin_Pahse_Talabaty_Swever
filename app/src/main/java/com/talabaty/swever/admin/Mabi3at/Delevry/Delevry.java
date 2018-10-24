package com.talabaty.swever.admin.Mabi3at.Delevry;

public class Delevry {
    int Id;
    double Value;
    boolean Type;
    int RegionId, ShopId;
    String RegionName;

    public Delevry(int id, double value, boolean type, int regionId, int shopId, String regionName) {
        Id = id;
        Value = value;
        Type = type;
        RegionId = regionId;
        ShopId = shopId;
        RegionName = regionName;
    }

    public Delevry() {
    }

    public int getId() {
        return Id;
    }

    public int getRegionId() {
        return RegionId;
    }

    public int getShopId() {
        return ShopId;
    }

    public double getValue() {
        return Value;
    }

    public String getRegionName() {
        return RegionName;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setType(boolean type) {
        Type = type;
    }

    public void setRegionId(int regionId) {
        RegionId = regionId;
    }

    public void setRegionName(String regionName) {
        RegionName = regionName;
    }

    public void setShopId(int shopId) {
        ShopId = shopId;
    }

    public void setValue(double value) {
        Value = value;
    }
}
