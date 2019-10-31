package com.sochic.sochic.SearchFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SubCategoryAPI implements Parcelable {

    public boolean success;
    public List<SubCategoryList> response;

    public static  class SubCategoryList implements Parcelable {
        public String sub_category_idx;
        public String name;

        protected SubCategoryList(Parcel in) {
            sub_category_idx = in.readString();
            name = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(sub_category_idx);
            dest.writeString(name);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<SubCategoryList> CREATOR = new Creator<SubCategoryList>() {
            @Override
            public SubCategoryList createFromParcel(Parcel in) {
                return new SubCategoryList(in);
            }

            @Override
            public SubCategoryList[] newArray(int size) {
                return new SubCategoryList[size];
            }
        };
    }

    protected SubCategoryAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(SubCategoryList.CREATOR);
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

    public static final Creator<SubCategoryAPI> CREATOR = new Creator<SubCategoryAPI>() {
        @Override
        public SubCategoryAPI createFromParcel(Parcel in) {
            return new SubCategoryAPI(in);
        }

        @Override
        public SubCategoryAPI[] newArray(int size) {
            return new SubCategoryAPI[size];
        }
    };
}
