package com.sochic.sochic.PayFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderTempCheckAPI implements Parcelable {

    public boolean success;
    public int type;

    protected OrderTempCheckAPI(Parcel in) {
        success = in.readByte() != 0;
        type = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeInt(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderTempCheckAPI> CREATOR = new Creator<OrderTempCheckAPI>() {
        @Override
        public OrderTempCheckAPI createFromParcel(Parcel in) {
            return new OrderTempCheckAPI(in);
        }

        @Override
        public OrderTempCheckAPI[] newArray(int size) {
            return new OrderTempCheckAPI[size];
        }
    };
}
