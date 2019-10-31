package com.sochic.sochic.ProductFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class OptionCountCheckAPI implements Parcelable {

    public boolean success;
    public int type;

    protected OptionCountCheckAPI(Parcel in) {
        success = in.readByte() != 0;
        type = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeInt(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OptionCountCheckAPI> CREATOR = new Creator<OptionCountCheckAPI>() {
        @Override
        public OptionCountCheckAPI createFromParcel(Parcel in) {
            return new OptionCountCheckAPI(in);
        }

        @Override
        public OptionCountCheckAPI[] newArray(int size) {
            return new OptionCountCheckAPI[size];
        }
    };
}
