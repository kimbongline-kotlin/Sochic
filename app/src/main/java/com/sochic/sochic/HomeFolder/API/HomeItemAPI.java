package com.sochic.sochic.HomeFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.sochic.sochic.Util.ApiMannager.ImageResponse;
import com.sochic.sochic.Util.ApiMannager.InterestList;

import java.util.List;

public class HomeItemAPI implements Parcelable {

    public boolean success;
    public List<HomeItemList> response;

    public static class HomeItemList implements  Parcelable {
        public String idx;
        public String id_user;
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


        protected HomeItemList(Parcel in) {
            idx = in.readString();
            id_user = in.readString();
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
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(idx);
            dest.writeString(id_user);
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
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<HomeItemList> CREATOR = new Creator<HomeItemList>() {
            @Override
            public HomeItemList createFromParcel(Parcel in) {
                return new HomeItemList(in);
            }

            @Override
            public HomeItemList[] newArray(int size) {
                return new HomeItemList[size];
            }
        };
    }

    protected HomeItemAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(HomeItemList.CREATOR);
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

    public static final Creator<HomeItemAPI> CREATOR = new Creator<HomeItemAPI>() {
        @Override
        public HomeItemAPI createFromParcel(Parcel in) {
            return new HomeItemAPI(in);
        }

        @Override
        public HomeItemAPI[] newArray(int size) {
            return new HomeItemAPI[size];
        }
    };
}
