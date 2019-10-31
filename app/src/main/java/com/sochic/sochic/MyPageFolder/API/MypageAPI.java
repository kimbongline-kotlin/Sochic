package com.sochic.sochic.MyPageFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class MypageAPI implements Parcelable {

    public boolean success;
    public String id_user;
    public String profile_image;
    public String nickname;
    public int following_number;
    public int point;
    public int pre_cnt;
    public int paid_cnt;
    public int delivery_pre_cnt;
    public int delivery_inf_cnt;
    public int delievry_com_cnt;
    public String email;


    protected MypageAPI(Parcel in) {
        success = in.readByte() != 0;
        id_user = in.readString();
        profile_image = in.readString();
        nickname = in.readString();
        following_number = in.readInt();
        point = in.readInt();
        pre_cnt = in.readInt();
        paid_cnt = in.readInt();
        delivery_pre_cnt = in.readInt();
        delivery_inf_cnt = in.readInt();
        delievry_com_cnt = in.readInt();
        email = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(id_user);
        dest.writeString(profile_image);
        dest.writeString(nickname);
        dest.writeInt(following_number);
        dest.writeInt(point);
        dest.writeInt(pre_cnt);
        dest.writeInt(paid_cnt);
        dest.writeInt(delivery_pre_cnt);
        dest.writeInt(delivery_inf_cnt);
        dest.writeInt(delievry_com_cnt);
        dest.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MypageAPI> CREATOR = new Creator<MypageAPI>() {
        @Override
        public MypageAPI createFromParcel(Parcel in) {
            return new MypageAPI(in);
        }

        @Override
        public MypageAPI[] newArray(int size) {
            return new MypageAPI[size];
        }
    };
}
