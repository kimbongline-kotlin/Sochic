package com.sochic.sochic.ProductFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProductContactAPI implements Parcelable {

    public boolean success;
    public List<ProductContactList> response;

    public static class  ProductContactList implements Parcelable {
        public String idx;
        public boolean answer_confirm;
        public String title;
        public String nickname;
        public String date;
        public String question;
        public String answer;
        public boolean open = false;

        protected ProductContactList(Parcel in) {
            idx = in.readString();
            answer_confirm = in.readByte() != 0;
            title = in.readString();
            nickname = in.readString();
            date = in.readString();
            question = in.readString();
            answer = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(idx);
            dest.writeByte((byte) (answer_confirm ? 1 : 0));
            dest.writeString(title);
            dest.writeString(nickname);
            dest.writeString(date);
            dest.writeString(question);
            dest.writeString(answer);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ProductContactList> CREATOR = new Creator<ProductContactList>() {
            @Override
            public ProductContactList createFromParcel(Parcel in) {
                return new ProductContactList(in);
            }

            @Override
            public ProductContactList[] newArray(int size) {
                return new ProductContactList[size];
            }
        };
    }

    protected ProductContactAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(ProductContactList.CREATOR);
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

    public static final Creator<ProductContactAPI> CREATOR = new Creator<ProductContactAPI>() {
        @Override
        public ProductContactAPI createFromParcel(Parcel in) {
            return new ProductContactAPI(in);
        }

        @Override
        public ProductContactAPI[] newArray(int size) {
            return new ProductContactAPI[size];
        }
    };
}
