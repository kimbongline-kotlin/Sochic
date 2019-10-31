package com.sochic.sochic.OrderFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class OrderDetailAPI implements Parcelable {

    public boolean success;
    public String date;
    public String o_code;
    public String sub_o_code;
    public String claim_code;
    public int product_price;
    public int d_price;
    public int p_price;
    public int c_price;
    public int t_price;
    public int p_type;
    public String c_name;
    public int save_point;
    public List<OrderAPI.OrderList.OrderItemList> o_response;
    public String o_name;
    public String o_phone;
    public String o_email;
    public String d_name;
    public String d_phone;
    public String d_address;
    public String d_post_number;
    public String d_memo;

    protected OrderDetailAPI(Parcel in) {
        success = in.readByte() != 0;
        date = in.readString();
        o_code = in.readString();
        sub_o_code = in.readString();
        claim_code = in.readString();
        product_price = in.readInt();
        d_price = in.readInt();
        p_price = in.readInt();
        c_price = in.readInt();
        t_price = in.readInt();
        p_type = in.readInt();
        c_name = in.readString();
        save_point = in.readInt();
        o_response = in.createTypedArrayList(OrderAPI.OrderList.OrderItemList.CREATOR);
        o_name = in.readString();
        o_phone = in.readString();
        o_email = in.readString();
        d_name = in.readString();
        d_phone = in.readString();
        d_address = in.readString();
        d_post_number = in.readString();
        d_memo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(date);
        dest.writeString(o_code);
        dest.writeString(sub_o_code);
        dest.writeString(claim_code);
        dest.writeInt(product_price);
        dest.writeInt(d_price);
        dest.writeInt(p_price);
        dest.writeInt(c_price);
        dest.writeInt(t_price);
        dest.writeInt(p_type);
        dest.writeString(c_name);
        dest.writeInt(save_point);
        dest.writeTypedList(o_response);
        dest.writeString(o_name);
        dest.writeString(o_phone);
        dest.writeString(o_email);
        dest.writeString(d_name);
        dest.writeString(d_phone);
        dest.writeString(d_address);
        dest.writeString(d_post_number);
        dest.writeString(d_memo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderDetailAPI> CREATOR = new Creator<OrderDetailAPI>() {
        @Override
        public OrderDetailAPI createFromParcel(Parcel in) {
            return new OrderDetailAPI(in);
        }

        @Override
        public OrderDetailAPI[] newArray(int size) {
            return new OrderDetailAPI[size];
        }
    };
}
