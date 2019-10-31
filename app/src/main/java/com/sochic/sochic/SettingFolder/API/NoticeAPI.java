package com.sochic.sochic.SettingFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class NoticeAPI implements Parcelable {

    public boolean success;
    public List<NoticeList> response;

    public static class NoticeList implements Parcelable {

        public String date;
        public String title;
        public String contents;
        public boolean open;

        protected NoticeList(Parcel in) {
            date = in.readString();
            title = in.readString();
            contents = in.readString();
            open = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(date);
            dest.writeString(title);
            dest.writeString(contents);
            dest.writeByte((byte) (open ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<NoticeList> CREATOR = new Creator<NoticeList>() {
            @Override
            public NoticeList createFromParcel(Parcel in) {
                return new NoticeList(in);
            }

            @Override
            public NoticeList[] newArray(int size) {
                return new NoticeList[size];
            }
        };
    }

    protected NoticeAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(NoticeList.CREATOR);
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

    public static final Creator<NoticeAPI> CREATOR = new Creator<NoticeAPI>() {
        @Override
        public NoticeAPI createFromParcel(Parcel in) {
            return new NoticeAPI(in);
        }

        @Override
        public NoticeAPI[] newArray(int size) {
            return new NoticeAPI[size];
        }
    };
}
