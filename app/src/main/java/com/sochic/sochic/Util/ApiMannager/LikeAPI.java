package com.sochic.sochic.Util.ApiMannager;

import android.os.Parcel;
import android.os.Parcelable;

public class LikeAPI implements Parcelable {

    public boolean success;
    public int number;

    protected LikeAPI(Parcel in) {
        success = in.readByte() != 0;
        number = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeInt(number);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LikeAPI> CREATOR = new Creator<LikeAPI>() {
        @Override
        public LikeAPI createFromParcel(Parcel in) {
            return new LikeAPI(in);
        }

        @Override
        public LikeAPI[] newArray(int size) {
            return new LikeAPI[size];
        }
    };
}
