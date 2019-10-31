package com.sochic.sochic.SellerFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SellerCalcurateAPI implements Parcelable {

    public boolean success;
    public int all_calcurate_price;
    public int all_price;
    public int all_fees;
    public List<SellerCalcurateList> response;

    public static class SellerCalcurateList implements Parcelable {
        public String date_1;
        public String date_2;
        public int calcurate_price;
        public int price;
        public int fees;
        public int benefit;
        public String refund;
        public String type;
        public String bank;
        public String bank_number;

        protected SellerCalcurateList(Parcel in) {
            date_1 = in.readString();
            date_2 = in.readString();
            calcurate_price = in.readInt();
            price = in.readInt();
            fees = in.readInt();
            benefit = in.readInt();
            refund = in.readString();
            type = in.readString();
            bank = in.readString();
            bank_number = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(date_1);
            dest.writeString(date_2);
            dest.writeInt(calcurate_price);
            dest.writeInt(price);
            dest.writeInt(fees);
            dest.writeInt(benefit);
            dest.writeString(refund);
            dest.writeString(type);
            dest.writeString(bank);
            dest.writeString(bank_number);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<SellerCalcurateList> CREATOR = new Creator<SellerCalcurateList>() {
            @Override
            public SellerCalcurateList createFromParcel(Parcel in) {
                return new SellerCalcurateList(in);
            }

            @Override
            public SellerCalcurateList[] newArray(int size) {
                return new SellerCalcurateList[size];
            }
        };
    }


    protected SellerCalcurateAPI(Parcel in) {
        success = in.readByte() != 0;
        all_calcurate_price = in.readInt();
        all_price = in.readInt();
        all_fees = in.readInt();
        response = in.createTypedArrayList(SellerCalcurateList.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeInt(all_calcurate_price);
        dest.writeInt(all_price);
        dest.writeInt(all_fees);
        dest.writeTypedList(response);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SellerCalcurateAPI> CREATOR = new Creator<SellerCalcurateAPI>() {
        @Override
        public SellerCalcurateAPI createFromParcel(Parcel in) {
            return new SellerCalcurateAPI(in);
        }

        @Override
        public SellerCalcurateAPI[] newArray(int size) {
            return new SellerCalcurateAPI[size];
        }
    };
}
