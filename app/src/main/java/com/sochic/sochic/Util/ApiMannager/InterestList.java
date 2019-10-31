package com.sochic.sochic.Util.ApiMannager;

import android.os.Parcel;
import android.os.Parcelable;

public class InterestList implements Parcelable {

    public String interest;

    protected InterestList(Parcel in) {
        interest = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(interest);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InterestList> CREATOR = new Creator<InterestList>() {
        @Override
        public InterestList createFromParcel(Parcel in) {
            return new InterestList(in);
        }

        @Override
        public InterestList[] newArray(int size) {
            return new InterestList[size];
        }
    };
}
