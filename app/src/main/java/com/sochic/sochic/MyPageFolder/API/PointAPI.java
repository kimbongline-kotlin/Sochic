package com.sochic.sochic.MyPageFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PointAPI implements Parcelable {

    public boolean success;
    public List<PointList>  response;

    public static class PointList implements Parcelable {
        public String date;
        public String  title;
        public String contents;
        public String point;

        protected PointList(Parcel in) {
            date = in.readString();
            title = in.readString();
            contents = in.readString();
            point = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(date);
            dest.writeString(title);
            dest.writeString(contents);
            dest.writeString(point);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<PointList> CREATOR = new Creator<PointList>() {
            @Override
            public PointList createFromParcel(Parcel in) {
                return new PointList(in);
            }

            @Override
            public PointList[] newArray(int size) {
                return new PointList[size];
            }
        };
    }

    protected PointAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(PointList.CREATOR);
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

    public static final Creator<PointAPI> CREATOR = new Creator<PointAPI>() {
        @Override
        public PointAPI createFromParcel(Parcel in) {
            return new PointAPI(in);
        }

        @Override
        public PointAPI[] newArray(int size) {
            return new PointAPI[size];
        }
    };
}
