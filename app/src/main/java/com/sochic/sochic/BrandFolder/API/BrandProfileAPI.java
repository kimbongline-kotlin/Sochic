package com.sochic.sochic.BrandFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class BrandProfileAPI implements Parcelable {

    public boolean success;
    public String profile_image;
    public String nickname;
    public String info;
    public boolean follow_confirm;
    public String insta_url;

    protected BrandProfileAPI(Parcel in) {
        success = in.readByte() != 0;
        profile_image = in.readString();
        nickname = in.readString();
        info = in.readString();
        follow_confirm = in.readByte() != 0;
        insta_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(profile_image);
        dest.writeString(nickname);
        dest.writeString(info);
        dest.writeByte((byte) (follow_confirm ? 1 : 0));
        dest.writeString(insta_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BrandProfileAPI> CREATOR = new Creator<BrandProfileAPI>() {
        @Override
        public BrandProfileAPI createFromParcel(Parcel in) {
            return new BrandProfileAPI(in);
        }

        @Override
        public BrandProfileAPI[] newArray(int size) {
            return new BrandProfileAPI[size];
        }
    };
}
