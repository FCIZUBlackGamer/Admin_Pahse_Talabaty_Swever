package com.talabaty.swever.admin.Montagat.AddMontag;

import java.io.Serializable;

public class Size implements Serializable {
    int Id;
    String Size;
    int SampleProductId ;


    public Size() {
    }

    public Size(String size) {
        this.Size = size;
    }

    public Size(int id, String size, int sampleProductId) {
        Size = size;
        Id = id;
        SampleProductId = sampleProductId;
    }

    public void setSize(String size) {
        Size = size;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setSampleProductId(int sampleProductId) {
        SampleProductId = sampleProductId;
    }

    public String getSize() {
        return Size;
    }
}
