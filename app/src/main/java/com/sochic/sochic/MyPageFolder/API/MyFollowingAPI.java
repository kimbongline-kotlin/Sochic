package com.sochic.sochic.MyPageFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MyFollowingAPI implements Parcelable {

    public boolean success;
    public List<MyFollowingList> response;

    public static class MyFollowingList implements Parcelable {
        public String id_user;
        public String profile_image;
        public String nickname;
        public boolean follow_confirm;

        protected MyFollowingList(Parcel in) {
            id_user = in.readString();
            profile_image = in.readString();
            nickname = in.readString();
            follow_confirm = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id_user);
            dest.writeString(profile_image);
            dest.writeString(nickname);
            dest.writeByte((byte) (follow_confirm ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<MyFollowingList> CREATOR = new Creator<MyFollowingList>() {
            @Override
            public MyFollowingList createFromParcel(Parcel in) {
                return new MyFollowingList(in);
            }

            @Override
            public MyFollowingList[] newArray(int size) {
                return new MyFollowingList[size];
            }
        };
    }

    protected MyFollowingAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(MyFollowingList.CREATOR);
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

    public static final Creator<MyFollowingAPI> CREATOR = new Creator<MyFollowingAPI>() {
        @Override
        public MyFollowingAPI createFromParcel(Parcel in) {
            return new MyFollowingAPI(in);
        }

        @Override
        public MyFollowingAPI[] newArray(int size) {
            return new MyFollowingAPI[size];
        }
    };
}
