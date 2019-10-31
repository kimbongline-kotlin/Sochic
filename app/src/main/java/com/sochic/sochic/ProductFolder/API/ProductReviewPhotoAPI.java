package com.sochic.sochic.ProductFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProductReviewPhotoAPI implements Parcelable {

    public boolean success;
    public List<ProductReviewPhotoList> response;

    public static class ProductReviewPhotoList implements Parcelable {
        public String image;

        protected ProductReviewPhotoList(Parcel in) {
            image = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(image);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ProductReviewPhotoList> CREATOR = new Creator<ProductReviewPhotoList>() {
            @Override
            public ProductReviewPhotoList createFromParcel(Parcel in) {
                return new ProductReviewPhotoList(in);
            }

            @Override
            public ProductReviewPhotoList[] newArray(int size) {
                return new ProductReviewPhotoList[size];
            }
        };
    }

    protected ProductReviewPhotoAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(ProductReviewPhotoList.CREATOR);
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

    public static final Creator<ProductReviewPhotoAPI> CREATOR = new Creator<ProductReviewPhotoAPI>() {
        @Override
        public ProductReviewPhotoAPI createFromParcel(Parcel in) {
            return new ProductReviewPhotoAPI(in);
        }

        @Override
        public ProductReviewPhotoAPI[] newArray(int size) {
            return new ProductReviewPhotoAPI[size];
        }
    };
}
