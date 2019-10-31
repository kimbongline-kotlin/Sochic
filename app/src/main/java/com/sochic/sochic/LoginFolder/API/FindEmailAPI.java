package com.sochic.sochic.LoginFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class FindEmailAPI implements Parcelable {

    public boolean success;
    public String email;
    public String id_user;
    public String date;


    protected FindEmailAPI(Parcel in) {
        success = in.readByte() != 0;
        email = in.readString();
        id_user = in.readString();
        date = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(email);
        dest.writeString(id_user);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FindEmailAPI> CREATOR = new Creator<FindEmailAPI>() {
        @Override
        public FindEmailAPI createFromParcel(Parcel in) {
            return new FindEmailAPI(in);
        }

        @Override
        public FindEmailAPI[] newArray(int size) {
            return new FindEmailAPI[size];
        }
    };
}
