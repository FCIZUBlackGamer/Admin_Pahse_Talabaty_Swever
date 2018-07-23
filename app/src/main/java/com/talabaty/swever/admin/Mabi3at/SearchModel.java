package com.talabaty.swever.admin.Mabi3at;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchModel {
    @SerializedName("CustomerId")
    @Expose
    String CustomerId;

    @SerializedName("From")
    @Expose
    String        From;

    @SerializedName("To")
    @Expose
    String       To;

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
    int x;


    public SearchModel(String customerId){
        CustomerId = customerId;
    }
    public SearchModel(String customerId, String from, String to) {
        CustomerId = customerId;
        From = from;
        To = to;
    }


    public SearchModel(String from, String to, String productId, int x) {
        From = from;
        To = to;
        ProductId = productId;
    }

    public SearchModel(String customerId, String from, String to, String fromReceived, String toReceived) {
        CustomerId = customerId;
        From = from;
        To = to;
        FromReceived = fromReceived;
        ToReceived = toReceived;
    }



    public SearchModel(String customerId, String from, String to, String fromReceived, String toReceived, String barcode, String productId) {
        CustomerId = customerId;
        From = from;
        To = to;
        FromReceived = fromReceived;
        ToReceived = toReceived;
        Barcode = barcode;
        ProductId = productId;
    }

    public String getBarcode() {
        return Barcode;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public String getFrom() {
        return From;
    }

    public String getFromReceived() {
        return FromReceived;
    }

    public String getProductId() {
        return ProductId;
    }

    public String getTo() {
        return To;
    }

    public String getToReceived() {
        return ToReceived;
    }
}
