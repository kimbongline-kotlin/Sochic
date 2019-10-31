package com.sochic.sochic.MyPageFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.sochic.sochic.Util.ApiMannager.ImageResponse;

import java.util.List;

public class CartAPI implements Parcelable {

    public boolean success;
    public List<CartList> response;

    public static class CartList implements Parcelable {

        public String profile_image;
        public String nickname;
        public String seller_id;
        public List<CartItemList> cart_response;
        public int delivery_price;


        public static class CartItemList implements  Parcelable {
            public String o_code;
            public String sub_o_index;
            public List<ImageResponse> img_response;
            public String name;
            public String option_name;
            public int cnt;
            public int price;
            public int add_price;
            public int sale_price;
            public boolean sale_confirm;
            public String idx_goods;


            protected CartItemList(Parcel in) {
                o_code = in.readString();
                sub_o_index = in.readString();
                img_response = in.createTypedArrayList(ImageResponse.CREATOR);
                name = in.readString();
                option_name = in.readString();
                cnt = in.readInt();
                price = in.readInt();
                add_price = in.readInt();
                sale_price = in.readInt();
                sale_confirm = in.readByte() != 0;
                idx_goods = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(o_code);
                dest.writeString(sub_o_index);
                dest.writeTypedList(img_response);
                dest.writeString(name);
                dest.writeString(option_name);
                dest.writeInt(cnt);
                dest.writeInt(price);
                dest.writeInt(add_price);
                dest.writeInt(sale_price);
                dest.writeByte((byte) (sale_confirm ? 1 : 0));
                dest.writeString(idx_goods);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<CartItemList> CREATOR = new Creator<CartItemList>() {
                @Override
                public CartItemList createFromParcel(Parcel in) {
                    return new CartItemList(in);
                }

                @Override
                public CartItemList[] newArray(int size) {
                    return new CartItemList[size];
                }
            };
        }


        protected CartList(Parcel in) {
            profile_image = in.readString();
            nickname = in.readString();
            seller_id = in.readString();
            cart_response = in.createTypedArrayList(CartItemList.CREATOR);
            delivery_price = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(profile_image);
            dest.writeString(nickname);
            dest.writeString(seller_id);
            dest.writeTypedList(cart_response);
            dest.writeInt(delivery_price);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<CartList> CREATOR = new Creator<CartList>() {
            @Override
            public CartList createFromParcel(Parcel in) {
                return new CartList(in);
            }

            @Override
            public CartList[] newArray(int size) {
                return new CartList[size];
            }
        };
    }

    protected CartAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(CartList.CREATOR);
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

    public static final Creator<CartAPI> CREATOR = new Creator<CartAPI>() {
        @Override
        public CartAPI createFromParcel(Parcel in) {
            return new CartAPI(in);
        }

        @Override
        public CartAPI[] newArray(int size) {
            return new CartAPI[size];
        }
    };
}

