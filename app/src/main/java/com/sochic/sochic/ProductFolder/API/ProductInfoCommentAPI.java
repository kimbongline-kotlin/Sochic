package com.sochic.sochic.ProductFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductInfoCommentAPI implements Parcelable {

    public boolean success;
    public String url;

    protected ProductInfoCommentAPI(Parcel in) {
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

    public static final Creator<ProductInfoCommentAPI> CREATOR = new Creator<ProductInfoCommentAPI>() {
        @Override
        public ProductInfoCommentAPI createFromParcel(Parcel in) {
            return new ProductInfoCommentAPI(in);
        }

        @Override
        public ProductInfoCommentAPI[] newArray(int size) {
            return new ProductInfoCommentAPI[size];
        }
    };
}
