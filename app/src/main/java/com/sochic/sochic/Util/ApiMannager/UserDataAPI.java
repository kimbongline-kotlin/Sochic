package com.sochic.sochic.Util.ApiMannager;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDataAPI implements Parcelable {

    public boolean success;
    public String id_user;
    public String user_id;
    public String sns_id;
    public String name;
    public String nickname;
    public int goal;
    public String recommend_code;
    public String created_date;
    public int login_type;
    public String phone;
    public String birthday;
    public String recommend_user;
    public String bank;
    public String bank_number;
    public String bank_name;
    public String email;
    public boolean nick_confirm;


    protected UserDataAPI(Parcel in) {
        success = in.readByte() != 0;
        id_user = in.readString();
        user_id = in.readString();
        sns_id = in.readString();
        name = in.readString();
        nickname = in.readString();
        goal = in.readInt();
        recommend_code = in.readString();
        created_date = in.readString();
        login_type = in.readInt();
        phone = in.readString();
        birthday = in.readString();
        recommend_user = in.readString();
        bank = in.readString();
        bank_number = in.readString();
        bank_name = in.readString();
        email = in.readString();
        nick_confirm = in.readByte() != 0;
    }

    public static final Creator<UserDataAPI> CREATOR = new Creator<UserDataAPI>() {
        @Override
        public UserDataAPI createFromParcel(Parcel in) {
            return new UserDataAPI(in);
        }

        @Override
        public UserDataAPI[] newArray(int size) {
            return new UserDataAPI[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(id_user);
        dest.writeString(user_id);
        dest.writeString(sns_id);
        dest.writeString(name);
        dest.writeString(nickname);
        dest.writeInt(goal);
        dest.writeString(recommend_code);
        dest.writeString(created_date);
        dest.writeInt(login_type);
        dest.writeString(phone);
        dest.writeString(birthday);
        dest.writeString(recommend_user);
        dest.writeString(bank);
        dest.writeString(bank_number);
        dest.writeString(bank_name);
        dest.writeString(email);
        dest.writeByte((byte) (nick_confirm ? 1 : 0));
    }
}
