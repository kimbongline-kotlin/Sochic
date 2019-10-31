package com.sochic.sochic.OrderFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class OrderShortInfoAPI implements Parcelable {

    public boolean success;
    public String date;
    public List<OrderAPI.OrderList.OrderItemList> response;


    protected OrderShortInfoAPI(Parcel in) {
        success = in.readByte() != 0;
        date = in.readString();
        response = in.createTypedArrayList(OrderAPI.OrderList.OrderItemList.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(date);
        dest.writeTypedList(response);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderShortInfoAPI> CREATOR = new Creator<OrderShortInfoAPI>() {
        @Override
        public OrderShortInfoAPI createFromParcel(Parcel in) {
            return new OrderShortInfoAPI(in);
        }

        @Override
        public OrderShortInfoAPI[] newArray(int size) {
            return new OrderShortInfoAPI[size];
        }
    };
}
