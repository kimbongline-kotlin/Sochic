package com.sochic.sochic.Util.ApiMannager;

import android.os.Parcel;
import android.os.Parcelable;

public class TrueFalseAPI implements Parcelable {
    public boolean success;

    protected TrueFalseAPI(Parcel in) {
        success = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrueFalseAPI> CREATOR = new Creator<TrueFalseAPI>() {
        @Override
        public TrueFalseAPI createFromParcel(Parcel in) {
            return new TrueFalseAPI(in);
        }

        @Override
        public TrueFalseAPI[] newArray(int size) {
            return new TrueFalseAPI[size];
        }
    };
}
