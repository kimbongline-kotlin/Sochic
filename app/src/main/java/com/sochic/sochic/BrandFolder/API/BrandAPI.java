package com.sochic.sochic.BrandFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BrandAPI implements Parcelable {

    public boolean success;
    public List<BrandList> response;

    public static class BrandList implements Parcelable {

        public String seller_id;
        public String nickname;
        public String profile_image;
        public boolean follow_confirm;
        public List<BrandProductList> product_response;

        public static class BrandProductList implements Parcelable {

            public String idx;
            public String image;

            protected BrandProductList(Parcel in) {
                idx = in.readString();
                image = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(idx);
                dest.writeString(image);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<BrandProductList> CREATOR = new Creator<BrandProductList>() {
                @Override
                public BrandProductList createFromParcel(Parcel in) {
                    return new BrandProductList(in);
                }

                @Override
                public BrandProductList[] newArray(int size) {
                    return new BrandProductList[size];
                }
            };
        }

        protected BrandList(Parcel in) {
            seller_id = in.readString();
            nickname = in.readString();
            profile_image = in.readString();
            follow_confirm = in.readByte() != 0;
            product_response = in.createTypedArrayList(BrandProductList.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(seller_id);
            dest.writeString(nickname);
            dest.writeString(profile_image);
            dest.writeByte((byte) (follow_confirm ? 1 : 0));
            dest.writeTypedList(product_response);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<BrandList> CREATOR = new Creator<BrandList>() {
            @Override
            public BrandList createFromParcel(Parcel in) {
                return new BrandList(in);
            }

            @Override
            public BrandList[] newArray(int size) {
                return new BrandList[size];
            }
        };
    }

    protected BrandAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(BrandList.CREATOR);
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

    public static final Creator<BrandAPI> CREATOR = new Creator<BrandAPI>() {
        @Override
        public BrandAPI createFromParcel(Parcel in) {
            return new BrandAPI(in);
        }

        @Override
        public BrandAPI[] newArray(int size) {
            return new BrandAPI[size];
        }
    };
}
