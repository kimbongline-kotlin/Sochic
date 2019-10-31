package com.sochic.sochic.PayFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MyCouponAPI implements Parcelable {

    public boolean success;
    public List<MyCouponList> response;

    public static class MyCouponList implements Parcelable {
        public String  c_idx;
        public String c_name;
        public boolean c_percent_bool;
        public int c_value;

        protected MyCouponList(Parcel in) {
            c_idx = in.readString();
            c_name = in.readString();
            c_percent_bool = in.readByte() != 0;
            c_value = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(c_idx);
            dest.writeString(c_name);
            dest.writeByte((byte) (c_percent_bool ? 1 : 0));
            dest.writeInt(c_value);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<MyCouponList> CREATOR = new Creator<MyCouponList>() {
            @Override
            public MyCouponList createFromParcel(Parcel in) {
                return new MyCouponList(in);
            }

            @Override
            public MyCouponList[] newArray(int size) {
                return new MyCouponList[size];
            }
        };
    }

    protected MyCouponAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(MyCouponList.CREATOR);
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

    public static final Creator<MyCouponAPI> CREATOR = new Creator<MyCouponAPI>() {
        @Override
        public MyCouponAPI createFromParcel(Parcel in) {
            return new MyCouponAPI(in);
        }

        @Override
        public MyCouponAPI[] newArray(int size) {
            return new MyCouponAPI[size];
        }
    };
}
