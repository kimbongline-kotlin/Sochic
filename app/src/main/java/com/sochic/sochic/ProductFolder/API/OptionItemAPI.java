package com.sochic.sochic.ProductFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class OptionItemAPI implements Parcelable {

    public boolean success;
    public List<OptionItemList> response;
    public String option_title;

    public static class OptionItemList implements Parcelable {
        public String o_idx;
        public String o_name;
        public int o_max_cnt;
        public int price;
        public int add_price;

        protected OptionItemList(Parcel in) {
            o_idx = in.readString();
            o_name = in.readString();
            o_max_cnt = in.readInt();
            price = in.readInt();
            add_price = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(o_idx);
            dest.writeString(o_name);
            dest.writeInt(o_max_cnt);
            dest.writeInt(price);
            dest.writeInt(add_price);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<OptionItemList> CREATOR = new Creator<OptionItemList>() {
            @Override
            public OptionItemList createFromParcel(Parcel in) {
                return new OptionItemList(in);
            }

            @Override
            public OptionItemList[] newArray(int size) {
                return new OptionItemList[size];
            }
        };
    }


    protected OptionItemAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(OptionItemList.CREATOR);
        option_title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeTypedList(response);
        dest.writeString(option_title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OptionItemAPI> CREATOR = new Creator<OptionItemAPI>() {
        @Override
        public OptionItemAPI createFromParcel(Parcel in) {
            return new OptionItemAPI(in);
        }

        @Override
        public OptionItemAPI[] newArray(int size) {
            return new OptionItemAPI[size];
        }
    };
}
