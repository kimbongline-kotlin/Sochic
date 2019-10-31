package com.sochic.sochic.MyPageFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CouponAPI implements Parcelable {

    public boolean success;
    public List<CouponList> response;

    public static class CouponList implements Parcelable {

        public String c_idx;
        public String c_name;
        public boolean c_percent_bool;
        public int c_value;
        public boolean c_down_cofirm;
        public boolean c_use_confirm;
        public int c_valid_value;
        public String c_nickname;
        public String c_date;


        protected CouponList(Parcel in) {
            c_idx = in.readString();
            c_name = in.readString();
            c_percent_bool = in.readByte() != 0;
            c_value = in.readInt();
            c_down_cofirm = in.readByte() != 0;
            c_use_confirm = in.readByte() != 0;
            c_valid_value = in.readInt();
            c_nickname = in.readString();
            c_date = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(c_idx);
            dest.writeString(c_name);
            dest.writeByte((byte) (c_percent_bool ? 1 : 0));
            dest.writeInt(c_value);
            dest.writeByte((byte) (c_down_cofirm ? 1 : 0));
            dest.writeByte((byte) (c_use_confirm ? 1 : 0));
            dest.writeInt(c_valid_value);
            dest.writeString(c_nickname);
            dest.writeString(c_date);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<CouponList> CREATOR = new Creator<CouponList>() {
            @Override
            public CouponList createFromParcel(Parcel in) {
                return new CouponList(in);
            }

            @Override
            public CouponList[] newArray(int size) {
                return new CouponList[size];
            }
        };
    }

    protected CouponAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(CouponList.CREATOR);
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

    public static final Creator<CouponAPI> CREATOR = new Creator<CouponAPI>() {
        @Override
        public CouponAPI createFromParcel(Parcel in) {
            return new CouponAPI(in);
        }

        @Override
        public CouponAPI[] newArray(int size) {
            return new CouponAPI[size];
        }
    };
}
