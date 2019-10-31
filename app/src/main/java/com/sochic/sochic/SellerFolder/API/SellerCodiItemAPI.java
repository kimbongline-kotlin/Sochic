package com.sochic.sochic.SellerFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.sochic.sochic.Util.ApiMannager.ImageResponse;

import java.util.List;

public class SellerCodiItemAPI implements Parcelable {

    public boolean success;
    public List<SellerCodiItemList> response;


    public static class SellerCodiItemList implements Parcelable {

        public List<ImageResponse> img_response;
        public String product_code;
        public String product_name;
        public boolean open;

        protected SellerCodiItemList(Parcel in) {
            img_response = in.createTypedArrayList(ImageResponse.CREATOR);
            product_code = in.readString();
            product_name = in.readString();
            open = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(img_response);
            dest.writeString(product_code);
            dest.writeString(product_name);
            dest.writeByte((byte) (open ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<SellerCodiItemList> CREATOR = new Creator<SellerCodiItemList>() {
            @Override
            public SellerCodiItemList createFromParcel(Parcel in) {
                return new SellerCodiItemList(in);
            }

            @Override
            public SellerCodiItemList[] newArray(int size) {
                return new SellerCodiItemList[size];
            }
        };
    }

    protected SellerCodiItemAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(SellerCodiItemList.CREATOR);
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

    public static final Creator<SellerCodiItemAPI> CREATOR = new Creator<SellerCodiItemAPI>() {
        @Override
        public SellerCodiItemAPI createFromParcel(Parcel in) {
            return new SellerCodiItemAPI(in);
        }

        @Override
        public SellerCodiItemAPI[] newArray(int size) {
            return new SellerCodiItemAPI[size];
        }
    };
}
