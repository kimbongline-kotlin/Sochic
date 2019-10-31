package com.sochic.sochic.ProductFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductInfoNoticeAPI implements Parcelable {

    public boolean success;
    public String url;


    protected ProductInfoNoticeAPI(Parcel in) {
        success = in.readByte() != 0;
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductInfoNoticeAPI> CREATOR = new Creator<ProductInfoNoticeAPI>() {
        @Override
        public ProductInfoNoticeAPI createFromParcel(Parcel in) {
            return new ProductInfoNoticeAPI(in);
        }

        @Override
        public ProductInfoNoticeAPI[] newArray(int size) {
            return new ProductInfoNoticeAPI[size];
        }
    };
}
