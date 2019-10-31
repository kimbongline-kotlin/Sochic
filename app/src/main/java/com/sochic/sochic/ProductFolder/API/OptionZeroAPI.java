package com.sochic.sochic.ProductFolder.API;

import android.os.Parcel;
import android.os.Parcelable;


public class OptionZeroAPI implements Parcelable {

    public boolean success;
    public String o_idx;
    public String o_name;
    public int o_max_cnt;
    public int price;
    public int add_price;

    protected OptionZeroAPI(Parcel in) {
        success = in.readByte() != 0;
        o_idx = in.readString();
        o_name = in.readString();
        o_max_cnt = in.readInt();
        price = in.readInt();
        add_price = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(o_idx);
        dest.writeString(o_name);
        dest.writeInt(o_max_cnt);
        dest.writeInt(price);
        dest.writeInt(add_price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OptionZeroAPI> CREATOR = new Creator<OptionZeroAPI>() {
        @Override
        public OptionZeroAPI createFromParcel(Parcel in) {
            return new OptionZeroAPI(in);
        }

        @Override
        public OptionZeroAPI[] newArray(int size) {
            return new OptionZeroAPI[size];
        }
    };
}
