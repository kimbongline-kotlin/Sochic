package com.sochic.sochic.OrderFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CancelReasonAPI implements Parcelable {

    public boolean success;
    public List<CancelReasonList> response;

    public static class CancelReasonList implements Parcelable {
        public String canidx;
        public String canname;

        protected CancelReasonList(Parcel in) {
            canidx = in.readString();
            canname = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(canidx);
            dest.writeString(canname);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<CancelReasonList> CREATOR = new Creator<CancelReasonList>() {
            @Override
            public CancelReasonList createFromParcel(Parcel in) {
                return new CancelReasonList(in);
            }

            @Override
            public CancelReasonList[] newArray(int size) {
                return new CancelReasonList[size];
            }
        };
    }

    protected CancelReasonAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(CancelReasonList.CREATOR);
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

    public static final Creator<CancelReasonAPI> CREATOR = new Creator<CancelReasonAPI>() {
        @Override
        public CancelReasonAPI createFromParcel(Parcel in) {
            return new CancelReasonAPI(in);
        }

        @Override
        public CancelReasonAPI[] newArray(int size) {
            return new CancelReasonAPI[size];
        }
    };
}
