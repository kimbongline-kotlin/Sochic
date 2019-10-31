package com.sochic.sochic.OrderFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderCancelInfoAPI implements Parcelable {

    public boolean success;
    public String title;

    protected OrderCancelInfoAPI(Parcel in) {
        success = in.readByte() != 0;
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderCancelInfoAPI> CREATOR = new Creator<OrderCancelInfoAPI>() {
        @Override
        public OrderCancelInfoAPI createFromParcel(Parcel in) {
            return new OrderCancelInfoAPI(in);
        }

        @Override
        public OrderCancelInfoAPI[] newArray(int size) {
            return new OrderCancelInfoAPI[size];
        }
    };
}
