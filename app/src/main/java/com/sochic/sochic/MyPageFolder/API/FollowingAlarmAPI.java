package com.sochic.sochic.MyPageFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class FollowingAlarmAPI implements Parcelable {

    public boolean success;
    public List<FollowingAlarmList> response;


    public static class FollowingAlarmList implements Parcelable {
        public String nickname;
        public String profile_image;
        public String seller_product_name;
        public String date;

        protected FollowingAlarmList(Parcel in) {
            nickname = in.readString();
            profile_image = in.readString();
            seller_product_name = in.readString();
            date = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(nickname);
            dest.writeString(profile_image);
            dest.writeString(seller_product_name);
            dest.writeString(date);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<FollowingAlarmList> CREATOR = new Creator<FollowingAlarmList>() {
            @Override
            public FollowingAlarmList createFromParcel(Parcel in) {
                return new FollowingAlarmList(in);
            }

            @Override
            public FollowingAlarmList[] newArray(int size) {
                return new FollowingAlarmList[size];
            }
        };
    }

    protected FollowingAlarmAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(FollowingAlarmList.CREATOR);
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

    public static final Creator<FollowingAlarmAPI> CREATOR = new Creator<FollowingAlarmAPI>() {
        @Override
        public FollowingAlarmAPI createFromParcel(Parcel in) {
            return new FollowingAlarmAPI(in);
        }

        @Override
        public FollowingAlarmAPI[] newArray(int size) {
            return new FollowingAlarmAPI[size];
        }
    };
}
