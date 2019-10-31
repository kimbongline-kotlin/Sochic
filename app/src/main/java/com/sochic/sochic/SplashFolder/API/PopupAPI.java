package com.sochic.sochic.SplashFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PopupAPI implements Parcelable {

    public boolean success;
    public List<PopupList> response;

    public static class PopupList implements Parcelable {
        public String image;
        public String url;

        protected PopupList(Parcel in) {
            image = in.readString();
            url = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(image);
            dest.writeString(url);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<PopupList> CREATOR = new Creator<PopupList>() {
            @Override
            public PopupList createFromParcel(Parcel in) {
                return new PopupList(in);
            }

            @Override
            public PopupList[] newArray(int size) {
                return new PopupList[size];
            }
        };
    }

    protected PopupAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(PopupList.CREATOR);
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

    public static final Creator<PopupAPI> CREATOR = new Creator<PopupAPI>() {
        @Override
        public PopupAPI createFromParcel(Parcel in) {
            return new PopupAPI(in);
        }

        @Override
        public PopupAPI[] newArray(int size) {
            return new PopupAPI[size];
        }
    };
}
