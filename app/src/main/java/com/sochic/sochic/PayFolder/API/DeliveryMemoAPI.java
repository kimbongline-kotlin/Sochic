package com.sochic.sochic.PayFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class DeliveryMemoAPI implements Parcelable {

    public boolean success;
    public List<DeliveryMemoList> response;

    public static class  DeliveryMemoList implements Parcelable {
        public String m_idx;
        public String m_title;

        protected DeliveryMemoList(Parcel in) {
            m_idx = in.readString();
            m_title = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(m_idx);
            dest.writeString(m_title);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<DeliveryMemoList> CREATOR = new Creator<DeliveryMemoList>() {
            @Override
            public DeliveryMemoList createFromParcel(Parcel in) {
                return new DeliveryMemoList(in);
            }

            @Override
            public DeliveryMemoList[] newArray(int size) {
                return new DeliveryMemoList[size];
            }
        };
    }

    protected DeliveryMemoAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(DeliveryMemoList.CREATOR);
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

    public static final Creator<DeliveryMemoAPI> CREATOR = new Creator<DeliveryMemoAPI>() {
        @Override
        public DeliveryMemoAPI createFromParcel(Parcel in) {
            return new DeliveryMemoAPI(in);
        }

        @Override
        public DeliveryMemoAPI[] newArray(int size) {
            return new DeliveryMemoAPI[size];
        }
    };
}
