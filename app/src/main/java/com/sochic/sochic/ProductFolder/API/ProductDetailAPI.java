package com.sochic.sochic.ProductFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.sochic.sochic.Util.ApiMannager.ImageResponse;
import com.sochic.sochic.Util.ApiMannager.InterestList;

import java.util.List;

public class ProductDetailAPI implements Parcelable {

    public boolean success;
    public String idx;
    public String profile_image;
    public String nickname;
    public boolean follow_confirm;
    public boolean heart_confirm;
    public boolean bookmark_confirm;
    public int heart_cnt;
    public String name;
    public String info;
    public String age_group;
    public int category;
    public String sub_category;
    public String open_date;
    public String server_date;
    public List<ImageResponse> img_response;
    public List<InterestList> interest;
    public int type;
    public int price;
    public int sale_price;
    public boolean sale_confirm;
    public String seller_id;


    protected ProductDetailAPI(Parcel in) {
        success = in.readByte() != 0;
        idx = in.readString();
        profile_image = in.readString();
        nickname = in.readString();
        follow_confirm = in.readByte() != 0;
        heart_confirm = in.readByte() != 0;
        bookmark_confirm = in.readByte() != 0;
        heart_cnt = in.readInt();
        name = in.readString();
        info = in.readString();
        age_group = in.readString();
        category = in.readInt();
        sub_category = in.readString();
        open_date = in.readString();
        server_date = in.readString();
        img_response = in.createTypedArrayList(ImageResponse.CREATOR);
        interest = in.createTypedArrayList(InterestList.CREATOR);
        type = in.readInt();
        price = in.readInt();
        sale_price = in.readInt();
        sale_confirm = in.readByte() != 0;
        seller_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(idx);
        dest.writeString(profile_image);
        dest.writeString(nickname);
        dest.writeByte((byte) (follow_confirm ? 1 : 0));
        dest.writeByte((byte) (heart_confirm ? 1 : 0));
        dest.writeByte((byte) (bookmark_confirm ? 1 : 0));
        dest.writeInt(heart_cnt);
        dest.writeString(name);
        dest.writeString(info);
        dest.writeString(age_group);
        dest.writeInt(category);
        dest.writeString(sub_category);
        dest.writeString(open_date);
        dest.writeString(server_date);
        dest.writeTypedList(img_response);
        dest.writeTypedList(interest);
        dest.writeInt(type);
        dest.writeInt(price);
        dest.writeInt(sale_price);
        dest.writeByte((byte) (sale_confirm ? 1 : 0));
        dest.writeString(seller_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductDetailAPI> CREATOR = new Creator<ProductDetailAPI>() {
        @Override
        public ProductDetailAPI createFromParcel(Parcel in) {
            return new ProductDetailAPI(in);
        }

        @Override
        public ProductDetailAPI[] newArray(int size) {
            return new ProductDetailAPI[size];
        }
    };
}
