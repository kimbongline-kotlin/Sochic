package com.sochic.sochic.PayFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderTempSavePointAPI implements Parcelable {

    public boolean success;
    public int point;

    protected OrderTempSavePointAPI(Parcel in) {
        success = in.readByte() != 0;
        point = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeInt(point);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderTempSavePointAPI> CREATOR = new Creator<OrderTempSavePointAPI>() {
        @Override
        public OrderTempSavePointAPI createFromParcel(Parcel in) {
            return new OrderTempSavePointAPI(in);
        }

        @Override
        public OrderTempSavePointAPI[] newArray(int size) {
            return new OrderTempSavePointAPI[size];
        }
    };
}
