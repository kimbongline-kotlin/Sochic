package com.sochic.sochic.OrderFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ReturnReasonAPI implements Parcelable {


    public boolean success;
    public List<ReturnReasonList> response;

    public static class ReturnReasonList implements Parcelable {
        public String reidx;
        public String rename;

        protected ReturnReasonList(Parcel in) {
            reidx = in.readString();
            rename = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(reidx);
            dest.writeString(rename);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ReturnReasonList> CREATOR = new Creator<ReturnReasonList>() {
            @Override
            public ReturnReasonList createFromParcel(Parcel in) {
                return new ReturnReasonList(in);
            }

            @Override
            public ReturnReasonList[] newArray(int size) {
                return new ReturnReasonList[size];
            }
        };
    }

    protected ReturnReasonAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(ReturnReasonList.CREATOR);
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

    public static final Creator<ReturnReasonAPI> CREATOR = new Creator<ReturnReasonAPI>() {
        @Override
        public ReturnReasonAPI createFromParcel(Parcel in) {
            return new ReturnReasonAPI(in);
        }

        @Override
        public ReturnReasonAPI[] newArray(int size) {
            return new ReturnReasonAPI[size];
        }
    };
}
