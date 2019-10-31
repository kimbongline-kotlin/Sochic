package com.sochic.sochic.SellerFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SellerCalcurateDetailAPI implements Parcelable {

    public boolean success;
    public List<SellerCalcurateDetailList> response;

    public static class SellerCalcurateDetailList implements Parcelable {

        public String o_code;
        public String product_o_code;
        public String division;
        public String name;
        public String o_name;
        public String date_1;
        public String date_2;
        public String date_3;
        public int price;
        public int fees;
        public int moo_fees;
        public boolean calcurate_confirm;


        protected SellerCalcurateDetailList(Parcel in) {
            o_code = in.readString();
            product_o_code = in.readString();
            division = in.readString();
            name = in.readString();
            o_name = in.readString();
            date_1 = in.readString();
            date_2 = in.readString();
            date_3 = in.readString();
            price = in.readInt();
            fees = in.readInt();
            moo_fees = in.readInt();
            calcurate_confirm = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(o_code);
            dest.writeString(product_o_code);
            dest.writeString(division);
            dest.writeString(name);
            dest.writeString(o_name);
            dest.writeString(date_1);
            dest.writeString(date_2);
            dest.writeString(date_3);
            dest.writeInt(price);
            dest.writeInt(fees);
            dest.writeInt(moo_fees);
            dest.writeByte((byte) (calcurate_confirm ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<SellerCalcurateDetailList> CREATOR = new Creator<SellerCalcurateDetailList>() {
            @Override
            public SellerCalcurateDetailList createFromParcel(Parcel in) {
                return new SellerCalcurateDetailList(in);
            }

            @Override
            public SellerCalcurateDetailList[] newArray(int size) {
                return new SellerCalcurateDetailList[size];
            }
        };
    }

    protected SellerCalcurateDetailAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(SellerCalcurateDetailList.CREATOR);
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

    public static final Creator<SellerCalcurateDetailAPI> CREATOR = new Creator<SellerCalcurateDetailAPI>() {
        @Override
        public SellerCalcurateDetailAPI createFromParcel(Parcel in) {
            return new SellerCalcurateDetailAPI(in);
        }

        @Override
        public SellerCalcurateDetailAPI[] newArray(int size) {
            return new SellerCalcurateDetailAPI[size];
        }
    };
}
