package com.sochic.sochic.MyPageFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class AddressAPI implements Parcelable {

    public boolean success;
    public List<AddressList> response;

    public static class AddressList implements Parcelable {
        public String d_idx;
        public String d_name;
        public String d_phone;
        public boolean d_selected;
        public String d_address;

        protected AddressList(Parcel in) {
            d_idx = in.readString();
            d_name = in.readString();
            d_phone = in.readString();
            d_selected = in.readByte() != 0;
            d_address = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(d_idx);
            dest.writeString(d_name);
            dest.writeString(d_phone);
            dest.writeByte((byte) (d_selected ? 1 : 0));
            dest.writeString(d_address);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<AddressList> CREATOR = new Creator<AddressList>() {
            @Override
            public AddressList createFromParcel(Parcel in) {
                return new AddressList(in);
            }

            @Override
            public AddressList[] newArray(int size) {
                return new AddressList[size];
            }
        };
    }

    protected AddressAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(AddressList.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeTypedList(response);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddressAPI> CREATOR = new Creator<AddressAPI>() {
        @Override
        public AddressAPI createFromParcel(Parcel in) {
            return new AddressAPI(in);
        }

        @Override
        public AddressAPI[] newArray(int size) {
            return new AddressAPI[size];
        }
    };
}
