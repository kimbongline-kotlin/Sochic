package com.sochic.sochic.SettingFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class FaqAPI implements Parcelable {


    public boolean success;
    public List<FaqList> response;

    public static class FaqList implements Parcelable {

        public String title;
        public String contents;
        public boolean open;


        protected FaqList(Parcel in) {
            title = in.readString();
            contents = in.readString();
            open = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(contents);
            dest.writeByte((byte) (open ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<FaqList> CREATOR = new Creator<FaqList>() {
            @Override
            public FaqList createFromParcel(Parcel in) {
                return new FaqList(in);
            }

            @Override
            public FaqList[] newArray(int size) {
                return new FaqList[size];
            }
        };
    }

    protected FaqAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(FaqList.CREATOR);
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

    public static final Creator<FaqAPI> CREATOR = new Creator<FaqAPI>() {
        @Override
        public FaqAPI createFromParcel(Parcel in) {
            return new FaqAPI(in);
        }

        @Override
        public FaqAPI[] newArray(int size) {
            return new FaqAPI[size];
        }
    };
}
