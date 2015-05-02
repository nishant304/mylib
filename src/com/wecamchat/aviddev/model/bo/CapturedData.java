package com.wecamchat.aviddev.model.bo;

import android.graphics.Bitmap;

public class CapturedData {

    private String filePath;
    private Bitmap smallImage;
    private Bitmap largeImage;
    private boolean isImage;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Bitmap getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(Bitmap smallImage) {
        this.smallImage = smallImage;
    }

    public Bitmap getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(Bitmap largeImage) {
        this.largeImage = largeImage;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean isImage) {
        this.isImage = isImage;
    }
}
