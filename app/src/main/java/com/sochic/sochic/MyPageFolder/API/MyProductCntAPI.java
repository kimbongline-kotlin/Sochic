package com.sochic.sochic.MyPageFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

public class MyProductCntAPI implements Parcelable {

    public boolean success;
    public int cart_cnt;
    public int book_cnt;

    protected MyProductCntAPI(Parcel in) {
        success = in.readByte() != 0;
        cart_cnt = in.readInt();
        book_cnt = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeInt(cart_cnt);
        dest.writeInt(book_cnt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyProductCntAPI> CREATOR = new Creator<MyProductCntAPI>() {
        @Override
        public MyProductCntAPI createFromParcel(Parcel in) {
            return new MyProductCntAPI(in);
        }

        @Override
        public MyProductCntAPI[] newArray(int size) {
            return new MyProductCntAPI[size];
        }
    };
}
