package com.talabaty.swever.admin.Montagat.AddMontag;

import android.net.Uri;

public class ImageSource {
    Uri Photo;
    public int Id ;
    public int SampleProductId ;


    public ImageSource(Uri source) {
        this.Photo = source;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setSampleProductId(int sampleProductId) {
        SampleProductId = sampleProductId;
    }

    public Uri getSource() {
        return Photo;
    }
}
