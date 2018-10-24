package com.talabaty.swever.admin.Mabi3atTrend;

public class BestSell {
    int ProductId;
    double Amountseller;
    double SellPrice;
    double Total;
    String ProductName;

    public BestSell(int productId, double amountseller, double sellPrice, double total, String productName) {
        ProductId = productId;
        Amountseller = amountseller;
        SellPrice = sellPrice;
        Total = total;
        ProductName = productName;
    }

    public double getAmountseller() {
        return Amountseller;
    }

    public int getProductId() {
        return ProductId;
    }

    public double getSellPrice() {
        return SellPrice;
    }

    public double getTotal() {
        return Total;
    }

    public String getProductName() {
        return ProductName;
    }
}
