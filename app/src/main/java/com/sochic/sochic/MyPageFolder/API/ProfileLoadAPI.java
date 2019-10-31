package com.sochic.sochic.MyPageFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.sochic.sochic.Util.ApiMannager.InterestList;

import java.util.List;

public class ProfileLoadAPI implements Parcelable {

    public boolean success;
    public String profile_image;
    public String email;
    public String birthday;
    public int market_value;
    public List<InterestList> interest_response;

    protected ProfileLoadAPI(Parcel in) {
        success = in.readByte() != 0;
        profile_image = in.readString();
        email = in.readString();
        birthday = in.readString();
        market_value = in.readInt();
        interest_response = in.createTypedArrayList(InterestList.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(profile_image);
        dest.writeString(email);
        dest.writeString(birthday);
        dest.writeInt(market_value);
        dest.writeTypedList(interest_response);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProfileLoadAPI> CREATOR = new Creator<ProfileLoadAPI>() {
        @Override
        public ProfileLoadAPI createFromParcel(Parcel in) {
            return new ProfileLoadAPI(in);
        }

        @Override
        public ProfileLoadAPI[] newArray(int size) {
            return new ProfileLoadAPI[size];
        }
    };
}
