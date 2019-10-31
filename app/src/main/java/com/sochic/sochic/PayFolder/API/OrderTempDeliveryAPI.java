package com.sochic.sochic.PayFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderTempDeliveryAPI implements Parcelable {

    public boolean success;
    public int d_price;
    public String url;

    protected OrderTempDeliveryAPI(Parcel in) {
        success = in.readByte() != 0;
        d_price = in.readInt();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeInt(d_price);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderTempDeliveryAPI> CREATOR = new Creator<OrderTempDeliveryAPI>() {
        @Override
        public OrderTempDeliveryAPI createFromParcel(Parcel in) {
            return new OrderTempDeliveryAPI(in);
        }

        @Override
        public OrderTempDeliveryAPI[] newArray(int size) {
            return new OrderTempDeliveryAPI[size];
        }
    };
}
