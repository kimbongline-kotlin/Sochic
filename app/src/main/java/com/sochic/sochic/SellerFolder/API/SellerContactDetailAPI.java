package com.sochic.sochic.SellerFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class SellerContactDetailAPI implements Parcelable {

    public boolean success;
    public String title;
    public String question;

    protected SellerContactDetailAPI(Parcel in) {
        success = in.readByte() != 0;
        title = in.readString();
        question = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(title);
        dest.writeString(question);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SellerContactDetailAPI> CREATOR = new Creator<SellerContactDetailAPI>() {
        @Override
        public SellerContactDetailAPI createFromParcel(Parcel in) {
            return new SellerContactDetailAPI(in);
        }

        @Override
        public SellerContactDetailAPI[] newArray(int size) {
            return new SellerContactDetailAPI[size];
        }
    };
}

