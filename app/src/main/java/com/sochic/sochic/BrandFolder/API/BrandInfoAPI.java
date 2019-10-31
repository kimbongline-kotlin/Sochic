package com.sochic.sochic.BrandFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class BrandInfoAPI implements Parcelable {

    public boolean success;
    public String height;
    public String top_size;
    public String bottom_size;
    public String foot_size;
    public String body_fit;
    public String shop_name;
    public String shop_ceo_name;
    public String shop_number;
    public String shop_tel_number;
    public String shop_address;
    public String center_cs_time;
    public String center_phone_number;
    public String center_address;

    protected BrandInfoAPI(Parcel in) {
        success = in.readByte() != 0;
        height = in.readString();
        top_size = in.readString();
        bottom_size = in.readString();
        foot_size = in.readString();
        body_fit = in.readString();
        shop_name = in.readString();
        shop_ceo_name = in.readString();
        shop_number = in.readString();
        shop_tel_number = in.readString();
        shop_address = in.readString();
        center_cs_time = in.readString();
        center_phone_number = in.readString();
        center_address = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(height);
        dest.writeString(top_size);
        dest.writeString(bottom_size);
        dest.writeString(foot_size);
        dest.writeString(body_fit);
        dest.writeString(shop_name);
        dest.writeString(shop_ceo_name);
        dest.writeString(shop_number);
        dest.writeString(shop_tel_number);
        dest.writeString(shop_address);
        dest.writeString(center_cs_time);
        dest.writeString(center_phone_number);
        dest.writeString(center_address);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BrandInfoAPI> CREATOR = new Creator<BrandInfoAPI>() {
        @Override
        public BrandInfoAPI createFromParcel(Parcel in) {
            return new BrandInfoAPI(in);
        }

        @Override
        public BrandInfoAPI[] newArray(int size) {
            return new BrandInfoAPI[size];
        }
    };
}
