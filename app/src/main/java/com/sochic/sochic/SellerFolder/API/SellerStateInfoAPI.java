package com.sochic.sochic.SellerFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class SellerStateInfoAPI implements Parcelable {

    public boolean success;
    public int pre_cnt;
    public int paid_cnt;
    public int delivery_pre_cnt;
    public int delivery_ing_cnt;
    public int delivery_com_cnt;
    public int type_one_cnt;
    public int type_two_cnt;
    public int type_three_cnt;

    protected SellerStateInfoAPI(Parcel in) {
        success = in.readByte() != 0;
        pre_cnt = in.readInt();
        paid_cnt = in.readInt();
        delivery_pre_cnt = in.readInt();
        delivery_ing_cnt = in.readInt();
        delivery_com_cnt = in.readInt();
        type_one_cnt = in.readInt();
        type_two_cnt = in.readInt();
        type_three_cnt = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeInt(pre_cnt);
        dest.writeInt(paid_cnt);
        dest.writeInt(delivery_pre_cnt);
        dest.writeInt(delivery_ing_cnt);
        dest.writeInt(delivery_com_cnt);
        dest.writeInt(type_one_cnt);
        dest.writeInt(type_two_cnt);
        dest.writeInt(type_three_cnt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SellerStateInfoAPI> CREATOR = new Creator<SellerStateInfoAPI>() {
        @Override
        public SellerStateInfoAPI createFromParcel(Parcel in) {
            return new SellerStateInfoAPI(in);
        }

        @Override
        public SellerStateInfoAPI[] newArray(int size) {
            return new SellerStateInfoAPI[size];
        }
    };
}
