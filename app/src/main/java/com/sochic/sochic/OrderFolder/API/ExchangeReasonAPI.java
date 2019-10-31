package com.sochic.sochic.OrderFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ExchangeReasonAPI implements Parcelable {

    public boolean success;
    public List<ExchageReasonList> response;

    public static class ExchageReasonList implements Parcelable {
        public String exidx;
        public String exname;


        protected ExchageReasonList(Parcel in) {
            exidx = in.readString();
            exname = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(exidx);
            dest.writeString(exname);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ExchageReasonList> CREATOR = new Creator<ExchageReasonList>() {
            @Override
            public ExchageReasonList createFromParcel(Parcel in) {
                return new ExchageReasonList(in);
            }

            @Override
            public ExchageReasonList[] newArray(int size) {
                return new ExchageReasonList[size];
            }
        };
    }


    protected ExchangeReasonAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(ExchageReasonList.CREATOR);
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

    public static final Creator<ExchangeReasonAPI> CREATOR = new Creator<ExchangeReasonAPI>() {
        @Override
        public ExchangeReasonAPI createFromParcel(Parcel in) {
            return new ExchangeReasonAPI(in);
        }

        @Override
        public ExchangeReasonAPI[] newArray(int size) {
            return new ExchangeReasonAPI[size];
        }
    };
}
