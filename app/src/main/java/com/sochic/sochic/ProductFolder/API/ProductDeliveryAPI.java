package com.sochic.sochic.ProductFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductDeliveryAPI implements Parcelable {

    public boolean success;
    public String url;

    protected ProductDeliveryAPI(Parcel in) {
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

    public static final Creator<ProductDeliveryAPI> CREATOR = new Creator<ProductDeliveryAPI>() {
        @Override
        public ProductDeliveryAPI createFromParcel(Parcel in) {
            return new ProductDeliveryAPI(in);
        }

        @Override
        public ProductDeliveryAPI[] newArray(int size) {
            return new ProductDeliveryAPI[size];
        }
    };
}
