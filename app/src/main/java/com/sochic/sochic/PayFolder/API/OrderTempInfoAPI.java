package com.sochic.sochic.PayFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.sochic.sochic.Util.ApiMannager.ImageResponse;

import java.util.List;

public class OrderTempInfoAPI implements Parcelable {

    public boolean success;
    public List<OrderTempInfoList> response;

    public static class  OrderTempInfoList implements Parcelable {

        public String o_code;
        public String sub_o_index;
        public List<ImageResponse> img_response;
        public String name;
        public String option_name;
        public int cnt;
        public int add_price;
        public int price;
        public int sale_price;
        public boolean sale_confirm;
        public boolean option_confirm;

        protected OrderTempInfoList(Parcel in) {
            o_code = in.readString();
            sub_o_index = in.readString();
            img_response = in.createTypedArrayList(ImageResponse.CREATOR);
            name = in.readString();
            option_name = in.readString();
            cnt = in.readInt();
            add_price = in.readInt();
            price = in.readInt();
            sale_price = in.readInt();
            sale_confirm = in.readByte() != 0;
            option_confirm = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(o_code);
            dest.writeString(sub_o_index);
            dest.writeTypedList(img_response);
            dest.writeString(name);
            dest.writeString(option_name);
            dest.writeInt(cnt);
            dest.writeInt(add_price);
            dest.writeInt(price);
            dest.writeInt(sale_price);
            dest.writeByte((byte) (sale_confirm ? 1 : 0));
            dest.writeByte((byte) (option_confirm ? 1 : 0));
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<OrderTempInfoList> CREATOR = new Creator<OrderTempInfoList>() {
            @Override
            public OrderTempInfoList createFromParcel(Parcel in) {
                return new OrderTempInfoList(in);
            }

            @Override
            public OrderTempInfoList[] newArray(int size) {
                return new OrderTempInfoList[size];
            }
        };
    }

    protected OrderTempInfoAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(OrderTempInfoList.CREATOR);
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

    public static final Creator<OrderTempInfoAPI> CREATOR = new Creator<OrderTempInfoAPI>() {
        @Override
        public OrderTempInfoAPI createFromParcel(Parcel in) {
            return new OrderTempInfoAPI(in);
        }

        @Override
        public OrderTempInfoAPI[] newArray(int size) {
            return new OrderTempInfoAPI[size];
        }
    };
}
