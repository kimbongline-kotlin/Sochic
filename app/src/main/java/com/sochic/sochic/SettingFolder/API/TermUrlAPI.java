package com.sochic.sochic.SettingFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class TermUrlAPI implements Parcelable {

    public boolean success;
    public String url;

    protected TermUrlAPI(Parcel in) {
        success = in.readByte() != 0;
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TermUrlAPI> CREATOR = new Creator<TermUrlAPI>() {
        @Override
        public TermUrlAPI createFromParcel(Parcel in) {
            return new TermUrlAPI(in);
        }

        @Override
        public TermUrlAPI[] newArray(int size) {
            return new TermUrlAPI[size];
        }
    };
}
