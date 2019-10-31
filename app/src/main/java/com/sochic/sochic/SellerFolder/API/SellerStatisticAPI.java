package com.sochic.sochic.SellerFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SellerStatisticAPI implements Parcelable {

    public boolean success;
    public List<DateList> date_response;
    public List<CntList> cnt_response;

    public static class DateList implements Parcelable {
        public String date;

        protected DateList(Parcel in) {
            date = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(date);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<DateList> CREATOR = new Creator<DateList>() {
            @Override
            public DateList createFromParcel(Parcel in) {
                return new DateList(in);
            }

            @Override
            public DateList[] newArray(int size) {
                return new DateList[size];
            }
        };
    }

    public static class CntList implements Parcelable {
        public int cnt;

        protected CntList(Parcel in) {
            cnt = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(cnt);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<CntList> CREATOR = new Creator<CntList>() {
            @Override
            public CntList createFromParcel(Parcel in) {
                return new CntList(in);
            }

            @Override
            public CntList[] newArray(int size) {
                return new CntList[size];
            }
        };
    }

    protected SellerStatisticAPI(Parcel in) {
        success = in.readByte() != 0;
        date_response = in.createTypedArrayList(DateList.CREATOR);
        cnt_response = in.createTypedArrayList(CntList.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeTypedList(date_response);
        dest.writeTypedList(cnt_response);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SellerStatisticAPI> CREATOR = new Creator<SellerStatisticAPI>() {
        @Override
        public SellerStatisticAPI createFromParcel(Parcel in) {
            return new SellerStatisticAPI(in);
        }

        @Override
        public SellerStatisticAPI[] newArray(int size) {
            return new SellerStatisticAPI[size];
        }
    };
}
