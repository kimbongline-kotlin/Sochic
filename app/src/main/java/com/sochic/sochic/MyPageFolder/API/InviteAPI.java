package com.sochic.sochic.MyPageFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class InviteAPI implements Parcelable {

    public boolean success;
    public String rec_code;
    public int cnt;
    public int save_point;

    protected InviteAPI(Parcel in) {
        success = in.readByte() != 0;
        rec_code = in.readString();
        cnt = in.readInt();
        save_point = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(rec_code);
        dest.writeInt(cnt);
        dest.writeInt(save_point);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InviteAPI> CREATOR = new Creator<InviteAPI>() {
        @Override
        public InviteAPI createFromParcel(Parcel in) {
            return new InviteAPI(in);
        }

        @Override
        public InviteAPI[] newArray(int size) {
            return new InviteAPI[size];
        }
    };
}
