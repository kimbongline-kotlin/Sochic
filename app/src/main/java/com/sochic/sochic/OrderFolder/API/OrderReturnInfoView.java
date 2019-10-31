package com.sochic.sochic.OrderFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderReturnInfoView implements Parcelable {

    public boolean success;
    public String title;
    public String detail_info;
    public String post_number;
    public String address;
    public String address_detail;
    public String bank_name;
    public String bank_number;

    protected OrderReturnInfoView(Parcel in) {
        success = in.readByte() != 0;
        title = in.readString();
        detail_info = in.readString();
        post_number = in.readString();
        address = in.readString();
        address_detail = in.readString();
        bank_name = in.readString();
        bank_number = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(title);
        dest.writeString(detail_info);
        dest.writeString(post_number);
        dest.writeString(address);
        dest.writeString(address_detail);
        dest.writeString(bank_name);
        dest.writeString(bank_number);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderReturnInfoView> CREATOR = new Creator<OrderReturnInfoView>() {
        @Override
        public OrderReturnInfoView createFromParcel(Parcel in) {
            return new OrderReturnInfoView(in);
        }

        @Override
        public OrderReturnInfoView[] newArray(int size) {
            return new OrderReturnInfoView[size];
        }
    };
}
