package com.sochic.sochic.ProductFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProductReviewAPI implements Parcelable {

    public boolean success;
    public List<ProductReviewList> response;

    public static class ProductReviewList implements Parcelable {

        public String idx;
        public String name;
        public float rate;
        public String nickname;
        public String created_date;
        public String contents;
        public List<ProductReviewPhotoAPI.ProductReviewPhotoList> img_response;


        protected ProductReviewList(Parcel in) {
            idx = in.readString();
            name = in.readString();
            rate = in.readFloat();
            nickname = in.readString();
            created_date = in.readString();
            contents = in.readString();
            img_response = in.createTypedArrayList(ProductReviewPhotoAPI.ProductReviewPhotoList.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(idx);
            dest.writeString(name);
            dest.writeFloat(rate);
            dest.writeString(nickname);
            dest.writeString(created_date);
            dest.writeString(contents);
            dest.writeTypedList(img_response);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ProductReviewList> CREATOR = new Creator<ProductReviewList>() {
            @Override
            public ProductReviewList createFromParcel(Parcel in) {
                return new ProductReviewList(in);
            }

            @Override
            public ProductReviewList[] newArray(int size) {
                return new ProductReviewList[size];
            }
        };
    }

    protected ProductReviewAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(ProductReviewList.CREATOR);
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

    public static final Creator<ProductReviewAPI> CREATOR = new Creator<ProductReviewAPI>() {
        @Override
        public ProductReviewAPI createFromParcel(Parcel in) {
            return new ProductReviewAPI(in);
        }

        @Override
        public ProductReviewAPI[] newArray(int size) {
            return new ProductReviewAPI[size];
        }
    };
}
