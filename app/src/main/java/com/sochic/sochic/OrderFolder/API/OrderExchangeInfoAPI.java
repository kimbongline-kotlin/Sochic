package com.sochic.sochic.OrderFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderExchangeInfoAPI implements Parcelable {

    public boolean success;
    public String title;
    public String detail_info;
    public String post_number;
    public String address;
    public String address_detail;
    public String exchange_url;

    protected OrderExchangeInfoAPI(Parcel in) {
        success = in.readByte() != 0;
        title = in.readString();
        detail_info = in.readString();
        post_number = in.readString();
        address = in.readString();
        address_detail = in.readString();
        exchange_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(title);
        dest.writeString(detail_info);
        dest.writeString(post_number);
        dest.writeString(address);
        dest.writeString(address_detail);
        dest.writeString(exchange_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderExchangeInfoAPI> CREATOR = new Creator<OrderExchangeInfoAPI>() {
        @Override
        public OrderExchangeInfoAPI createFromParcel(Parcel in) {
            return new OrderExchangeInfoAPI(in);
        }

        @Override
        public OrderExchangeInfoAPI[] newArray(int size) {
            return new OrderExchangeInfoAPI[size];
        }
    };
}
