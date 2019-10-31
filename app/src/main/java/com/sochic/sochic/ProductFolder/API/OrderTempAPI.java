package com.sochic.sochic.ProductFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderTempAPI implements Parcelable {

    public boolean success;
    public String o_code;

    protected OrderTempAPI(Parcel in) {
        success = in.readByte() != 0;
        o_code = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(o_code);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderTempAPI> CREATOR = new Creator<OrderTempAPI>() {
        @Override
        public OrderTempAPI createFromParcel(Parcel in) {
            return new OrderTempAPI(in);
        }

        @Override
        public OrderTempAPI[] newArray(int size) {
            return new OrderTempAPI[size];
        }
    };
}
