package com.sochic.sochic.SearchFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SubCategorySearchAPI implements Parcelable {

    public boolean success;
    public List<SubCategorySearchList> response;

    public static class SubCategorySearchList implements Parcelable {
        public String idx;
        public String image;
        public String name;
        public int price;
        public int sale_price;
        public boolean sale_confirm;

        protected SubCategorySearchList(Parcel in) {
            idx = in.readString();
            image = in.readString();
            name = in.readString();
            price = in.readInt();
            sale_price = in.readInt();
            sale_confirm = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(idx);
            dest.writeString(image);
            dest.writeString(name);
            dest.writeInt(price);
            dest.writeInt(sale_price);
            dest.writeByte((byte) (sale_confirm ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<SubCategorySearchList> CREATOR = new Creator<SubCategorySearchList>() {
            @Override
            public SubCategorySearchList createFromParcel(Parcel in) {
                return new SubCategorySearchList(in);
            }

            @Override
            public SubCategorySearchList[] newArray(int size) {
                return new SubCategorySearchList[size];
            }
        };
    }

    protected SubCategorySearchAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(SubCategorySearchList.CREATOR);
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

    public static final Creator<SubCategorySearchAPI> CREATOR = new Creator<SubCategorySearchAPI>() {
        @Override
        public SubCategorySearchAPI createFromParcel(Parcel in) {
            return new SubCategorySearchAPI(in);
        }

        @Override
        public SubCategorySearchAPI[] newArray(int size) {
            return new SubCategorySearchAPI[size];
        }
    };
}
