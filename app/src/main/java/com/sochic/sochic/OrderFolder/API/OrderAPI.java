package com.sochic.sochic.OrderFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.sochic.sochic.Util.ApiMannager.ImageResponse;

import java.util.List;

public class OrderAPI implements Parcelable {

    public boolean success;
    public List<OrderList> response;

    public static class OrderList implements Parcelable {
        public String date;
        public String o_code;
        public String sub_o_code;
        public int order_confirm;
        public List<OrderItemList> o_response;

        public static class OrderItemList implements Parcelable{

            public String o_code;
            public String claim_code;
            public String sub_o_index;
            public List<ImageResponse> img_response;
            public String name;
            public String option_name;
            public int cnt;
            public int add_price;
            public int price;
            public int sale_price;
            public boolean sale_confirm;
            public boolean select = false;

            protected OrderItemList(Parcel in) {
                o_code = in.readString();
                claim_code = in.readString();
                sub_o_index = in.readString();
                img_response = in.createTypedArrayList(ImageResponse.CREATOR);
                name = in.readString();
                option_name = in.readString();
                cnt = in.readInt();
                add_price = in.readInt();
                price = in.readInt();
                sale_price = in.readInt();
                sale_confirm = in.readByte() != 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(o_code);
                dest.writeString(claim_code);
                dest.writeString(sub_o_index);
                dest.writeTypedList(img_response);
                dest.writeString(name);
                dest.writeString(option_name);
                dest.writeInt(cnt);
                dest.writeInt(add_price);
                dest.writeInt(price);
                dest.writeInt(sale_price);
                dest.writeByte((byte) (sale_confirm ? 1 : 0));
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<OrderItemList> CREATOR = new Creator<OrderItemList>() {
                @Override
                public OrderItemList createFromParcel(Parcel in) {
                    return new OrderItemList(in);
                }

                @Override
                public OrderItemList[] newArray(int size) {
                    return new OrderItemList[size];
                }
            };
        }


        protected OrderList(Parcel in) {
            date = in.readString();
            o_code = in.readString();
            sub_o_code = in.readString();
            order_confirm = in.readInt();
            o_response = in.createTypedArrayList(OrderItemList.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(date);
            dest.writeString(o_code);
            dest.writeString(sub_o_code);
            dest.writeInt(order_confirm);
            dest.writeTypedList(o_response);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<OrderList> CREATOR = new Creator<OrderList>() {
            @Override
            public OrderList createFromParcel(Parcel in) {
                return new OrderList(in);
            }

            @Override
            public OrderList[] newArray(int size) {
                return new OrderList[size];
            }
        };
    }


    protected OrderAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(OrderList.CREATOR);
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

    public static final Creator<OrderAPI> CREATOR = new Creator<OrderAPI>() {
        @Override
        public OrderAPI createFromParcel(Parcel in) {
            return new OrderAPI(in);
        }

        @Override
        public OrderAPI[] newArray(int size) {
            return new OrderAPI[size];
        }
    };
}
