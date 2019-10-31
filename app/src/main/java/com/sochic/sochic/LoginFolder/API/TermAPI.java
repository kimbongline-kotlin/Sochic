package com.sochic.sochic.LoginFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class TermAPI implements Parcelable {

    public boolean success;
    public String url_1;
    public String url_2;


    protected TermAPI(Parcel in) {
        success = in.readByte() != 0;
        url_1 = in.readString();
        url_2 = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(url_1);
        dest.writeString(url_2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TermAPI> CREATOR = new Creator<TermAPI>() {
        @Override
        public TermAPI createFromParcel(Parcel in) {
            return new TermAPI(in);
        }

        @Override
        public TermAPI[] newArray(int size) {
            return new TermAPI[size];
        }
    };
}
