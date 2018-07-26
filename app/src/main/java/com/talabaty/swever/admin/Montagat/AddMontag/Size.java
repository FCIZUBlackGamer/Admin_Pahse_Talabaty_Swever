package com.talabaty.swever.admin.Montagat.AddMontag;

public class Size {
    String Size;
    String Id;
    String SampleProductId ;


    public Size(String size) {
        this.Size = size;
    }

    public Size(String size, String id, String sampleProductId) {
        Size = size;
        Id = id;
        SampleProductId = sampleProductId;
    }

    public void setSize(String size) {
        Size = size;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setSampleProductId(String sampleProductId) {
        SampleProductId = sampleProductId;
    }

    public String getSize() {
        return Size;
    }
}
