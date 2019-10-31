package com.sochic.sochic.MyPageFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class AddressInfoAPI implements Parcelable {

    public boolean success;
    public String name;
    public String phone;
    public String post_number;
    public String address;
    public String address_detail;

    protected AddressInfoAPI(Parcel in) {
        success = in.readByte() != 0;
        name = in.readString();
        phone = in.readString();
        post_number = in.readString();
        address = in.readString();
        address_detail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(post_number);
        dest.writeString(address);
        dest.writeString(address_detail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddressInfoAPI> CREATOR = new Creator<AddressInfoAPI>() {
        @Override
        public AddressInfoAPI createFromParcel(Parcel in) {
            return new AddressInfoAPI(in);
        }

        @Override
        public AddressInfoAPI[] newArray(int size) {
            return new AddressInfoAPI[size];
        }
    };
}
