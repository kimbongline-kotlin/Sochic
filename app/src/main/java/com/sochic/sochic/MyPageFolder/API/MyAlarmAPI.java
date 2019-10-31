package com.sochic.sochic.MyPageFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MyAlarmAPI implements Parcelable {

    public boolean success;
    public List<MyAlarmList> response;

    public static class MyAlarmList implements Parcelable {
        public String contents;
        public String date;

        protected MyAlarmList(Parcel in) {
            contents = in.readString();
            date = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(contents);
            dest.writeString(date);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<MyAlarmList> CREATOR = new Creator<MyAlarmList>() {
            @Override
            public MyAlarmList createFromParcel(Parcel in) {
                return new MyAlarmList(in);
            }

            @Override
            public MyAlarmList[] newArray(int size) {
                return new MyAlarmList[size];
            }
        };
    }

    protected MyAlarmAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(MyAlarmList.CREATOR);
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

    public static final Creator<MyAlarmAPI> CREATOR = new Creator<MyAlarmAPI>() {
        @Override
        public MyAlarmAPI createFromParcel(Parcel in) {
            return new MyAlarmAPI(in);
        }

        @Override
        public MyAlarmAPI[] newArray(int size) {
            return new MyAlarmAPI[size];
        }
    };
}
