package com.sochic.sochic.SellerFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class SellerReDeliveryAPI implements Parcelable {

    public boolean success;
    public String info;
    public String url;

    protected SellerReDeliveryAPI(Parcel in) {
        success = in.readByte() != 0;
        info = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(info);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SellerReDeliveryAPI> CREATOR = new Creator<SellerReDeliveryAPI>() {
        @Override
        public SellerReDeliveryAPI createFromParcel(Parcel in) {
            return new SellerReDeliveryAPI(in);
        }

        @Override
        public SellerReDeliveryAPI[] newArray(int size) {
            return new SellerReDeliveryAPI[size];
        }
    };
}
