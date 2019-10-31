package com.sochic.sochic.OrderFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderStateAPI implements Parcelable {

    public int order_confirm;

    protected OrderStateAPI(Parcel in) {
        order_confirm = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(order_confirm);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderStateAPI> CREATOR = new Creator<OrderStateAPI>() {
        @Override
        public OrderStateAPI createFromParcel(Parcel in) {
            return new OrderStateAPI(in);
        }

        @Override
        public OrderStateAPI[] newArray(int size) {
            return new OrderStateAPI[size];
        }
    };
}
