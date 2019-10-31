package com.sochic.sochic.MyPageFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class MyPointAPI implements Parcelable {

    public boolean success;
    public int my_point;

    protected MyPointAPI(Parcel in) {
        success = in.readByte() != 0;
        my_point = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeInt(my_point);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyPointAPI> CREATOR = new Creator<MyPointAPI>() {
        @Override
        public MyPointAPI createFromParcel(Parcel in) {
            return new MyPointAPI(in);
        }

        @Override
        public MyPointAPI[] newArray(int size) {
            return new MyPointAPI[size];
        }
    };
}
