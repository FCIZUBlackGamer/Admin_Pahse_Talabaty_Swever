package com.talabaty.swever.admin.Mabi3at;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchModel {
    @SerializedName("CustomerId")
    @Expose
    String CustomerId;

    @SerializedName("ShopId")
    @Expose
    String        ShopId;

    @SerializedName("UserId")
    @Expose
    String       UserId;

    @SerializedName("FromReceived")
    @Expose
    String       FromReceived;

    @SerializedName("ToReceived")
    @Expose
    String      ToReceived;

    @SerializedName("Barcode")
    @Expose
    String      Barcode;

    @SerializedName("ProductId")
    @Expose
    String      ProductId;

    @SerializedName("x")
    @Expose
    String      x;

    @SerializedName("type")
    @Expose
    String      type;


    public SearchModel(String customerId, String shopId, String userId, String x, String type){
        CustomerId = customerId;
        ShopId = shopId;
        UserId = userId;
        this.x = x;
        this.type = type;
    }
    public SearchModel(String shopId, String userId, String x, String type) {
        ShopId = shopId;
        UserId = userId;
        this.x = x;
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }
    //    public SearchModel(String from, String to, String productId, int x) {
//        From = from;
//        To = to;
//        ProductId = productId;
//    }
//
//    public SearchModel(String customerId, String from, String to, String fromReceived, String toReceived) {
//        CustomerId = customerId;
//        From = from;
//        To = to;
//        FromReceived = fromReceived;
//        ToReceived = toReceived;
//    }
//
//
//
//    public SearchModel(String customerId, String from, String to, String fromReceived, String toReceived, String barcode, String productId) {
//        CustomerId = customerId;
//        From = from;
//        To = to;
//        FromReceived = fromReceived;
//        ToReceived = toReceived;
//        Barcode = barcode;
//        ProductId = productId;
//    }

    public String getBarcode() {
        return Barcode;
    }

    public String getCustomerId() {
        return CustomerId;
    }


    public String getFromReceived() {
        return FromReceived;
    }

    public String getProductId() {
        return ProductId;
    }

    public String getToReceived() {
        return ToReceived;
    }

    public String getShopId() {
        return ShopId;
    }

    public String getUserId() {
        return UserId;
    }

    public String getType() {
        return type;
    }

    public String getX() {
        return x;
    }
}
