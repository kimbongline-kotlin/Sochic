package com.sochic.sochic.SplashFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class SplashAPI implements Parcelable {

    public boolean success;
    public String image;


    protected SplashAPI(Parcel in) {
        success = in.readByte() != 0;
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SplashAPI> CREATOR = new Creator<SplashAPI>() {
        @Override
        public SplashAPI createFromParcel(Parcel in) {
            return new SplashAPI(in);
        }

        @Override
        public SplashAPI[] newArray(int size) {
            return new SplashAPI[size];
        }
    };
}
