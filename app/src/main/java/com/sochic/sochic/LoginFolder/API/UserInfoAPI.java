package com.sochic.sochic.LoginFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfoAPI implements Parcelable {

    public boolean success;
    public String id_user;
    public String email;
    public String nickname;
    public String profile_image;
    public String birthday;

    protected UserInfoAPI(Parcel in) {
        success = in.readByte() != 0;
        id_user = in.readString();
        email = in.readString();
        nickname = in.readString();
        profile_image = in.readString();
        birthday = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(id_user);
        dest.writeString(email);
        dest.writeString(nickname);
        dest.writeString(profile_image);
        dest.writeString(birthday);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfoAPI> CREATOR = new Creator<UserInfoAPI>() {
        @Override
        public UserInfoAPI createFromParcel(Parcel in) {
            return new UserInfoAPI(in);
        }

        @Override
        public UserInfoAPI[] newArray(int size) {
            return new UserInfoAPI[size];
        }
    };
}
