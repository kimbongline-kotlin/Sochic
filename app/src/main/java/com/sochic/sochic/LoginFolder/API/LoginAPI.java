package com.sochic.sochic.LoginFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginAPI implements Parcelable {

    public boolean success;
    public String id_user;

    protected LoginAPI(Parcel in) {
        success = in.readByte() != 0;
        id_user = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(id_user);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginAPI> CREATOR = new Creator<LoginAPI>() {
        @Override
        public LoginAPI createFromParcel(Parcel in) {
            return new LoginAPI(in);
        }

        @Override
        public LoginAPI[] newArray(int size) {
            return new LoginAPI[size];
        }
    };
}
