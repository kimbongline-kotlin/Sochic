package com.sochic.sochic.MyPageFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ReviewAPI implements Parcelable {

    public boolean success;
    public List<ReviewList> response;

    public static class  ReviewList implements Parcelable {
        public String o_code;
        public String sub_o_index;
        public String image;
        public String name;
        public String option_name;
        public int cnt;
        public int price;
        public int sale_price;
        public boolean sale_confirm;
        public float rate;
        public boolean review_confirm;

        protected ReviewList(Parcel in) {
            o_code = in.readString();
            sub_o_index = in.readString();
            image = in.readString();
            name = in.readString();
            option_name = in.readString();
            cnt = in.readInt();
            price = in.readInt();
            sale_price = in.readInt();
            sale_confirm = in.readByte() != 0;
            rate = in.readFloat();
            review_confirm = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(o_code);
            dest.writeString(sub_o_index);
            dest.writeString(image);
            dest.writeString(name);
            dest.writeString(option_name);
            dest.writeInt(cnt);
            dest.writeInt(price);
            dest.writeInt(sale_price);
            dest.writeByte((byte) (sale_confirm ? 1 : 0));
            dest.writeFloat(rate);
            dest.writeByte((byte) (review_confirm ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ReviewList> CREATOR = new Creator<ReviewList>() {
            @Override
            public ReviewList createFromParcel(Parcel in) {
                return new ReviewList(in);
            }

            @Override
            public ReviewList[] newArray(int size) {
                return new ReviewList[size];
            }
        };
    }

    protected ReviewAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(ReviewList.CREATOR);
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

    public static final Creator<ReviewAPI> CREATOR = new Creator<ReviewAPI>() {
        @Override
        public ReviewAPI createFromParcel(Parcel in) {
            return new ReviewAPI(in);
        }

        @Override
        public ReviewAPI[] newArray(int size) {
            return new ReviewAPI[size];
        }
    };
}
