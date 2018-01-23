package com.example.templated;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Banner implements Parcelable {
    @SerializedName("visible")
    @Expose
    private Boolean visible;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("url")
    @Expose
    private String url;

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.visible);
        dest.writeString(this.img);
        dest.writeString(this.url);
    }

    public Banner() {
    }

    protected Banner(Parcel in) {
        this.visible = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.img = in.readString();
        this.url = in.readString();
    }

    public static final Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel source) {
            return new Banner(source);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };
}
