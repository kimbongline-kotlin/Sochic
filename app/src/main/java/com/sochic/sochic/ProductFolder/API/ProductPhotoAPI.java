package com.sochic.sochic.ProductFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProductPhotoAPI implements Parcelable {

    public boolean success;
    public List<ProductPhotoList> response;

    public static class ProductPhotoList implements Parcelable {
        public String image;

        protected ProductPhotoList(Parcel in) {
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

        public static final Creator<ProductPhotoList> CREATOR = new Creator<ProductPhotoList>() {
            @Override
            public ProductPhotoList createFromParcel(Parcel in) {
                return new ProductPhotoList(in);
            }

            @Override
            public ProductPhotoList[] newArray(int size) {
                return new ProductPhotoList[size];
            }
        };
    }

    protected ProductPhotoAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(ProductPhotoList.CREATOR);
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

    public static final Creator<ProductPhotoAPI> CREATOR = new Creator<ProductPhotoAPI>() {
        @Override
        public ProductPhotoAPI createFromParcel(Parcel in) {
            return new ProductPhotoAPI(in);
        }

        @Override
        public ProductPhotoAPI[] newArray(int size) {
            return new ProductPhotoAPI[size];
        }
    };
}
