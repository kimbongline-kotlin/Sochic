package com.sochic.sochic.Util.ApiMannager;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageResponse implements Parcelable {

    public String image;

    protected ImageResponse(Parcel in) {
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageResponse> CREATOR = new Creator<ImageResponse>() {
        @Override
        public ImageResponse createFromParcel(Parcel in) {
            return new ImageResponse(in);
        }

        @Override
        public ImageResponse[] newArray(int size) {
            return new ImageResponse[size];
        }
    };
}
