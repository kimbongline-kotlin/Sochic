package com.sochic.sochic.SplashFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GuideAPI implements Parcelable {

    public boolean success;
    public List<GuideList> response;

    public static class GuideList implements Parcelable {
        public String image;

        protected GuideList(Parcel in) {
            image = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(image);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<GuideList> CREATOR = new Creator<GuideList>() {
            @Override
            public GuideList createFromParcel(Parcel in) {
                return new GuideList(in);
            }

            @Override
            public GuideList[] newArray(int size) {
                return new GuideList[size];
            }
        };
    }

    protected GuideAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(GuideList.CREATOR);
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

    public static final Creator<GuideAPI> CREATOR = new Creator<GuideAPI>() {
        @Override
        public GuideAPI createFromParcel(Parcel in) {
            return new GuideAPI(in);
        }

        @Override
        public GuideAPI[] newArray(int size) {
            return new GuideAPI[size];
        }
    };
}
