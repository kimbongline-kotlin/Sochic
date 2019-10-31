package com.sochic.sochic.OrderFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderExchangePriceAPI implements Parcelable {

    public boolean success;
    public int exchange_price;

    protected OrderExchangePriceAPI(Parcel in) {
        success = in.readByte() != 0;
        exchange_price = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeInt(exchange_price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderExchangePriceAPI> CREATOR = new Creator<OrderExchangePriceAPI>() {
        @Override
        public OrderExchangePriceAPI createFromParcel(Parcel in) {
            return new OrderExchangePriceAPI(in);
        }

        @Override
        public OrderExchangePriceAPI[] newArray(int size) {
            return new OrderExchangePriceAPI[size];
        }
    };
}
