package com.sochic.sochic.SearchFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SearchTagAPI implements Parcelable {

    public boolean success;
    public List<SearchTagList> response;

    public static class  SearchTagList implements Parcelable {
        public String tag_cnt;
        public int cnt;

        protected SearchTagList(Parcel in) {
            tag_cnt = in.readString();
            cnt = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(tag_cnt);
            dest.writeInt(cnt);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<SearchTagList> CREATOR = new Creator<SearchTagList>() {
            @Override
            public SearchTagList createFromParcel(Parcel in) {
                return new SearchTagList(in);
            }

            @Override
            public SearchTagList[] newArray(int size) {
                return new SearchTagList[size];
            }
        };
    }

    protected SearchTagAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(SearchTagList.CREATOR);
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

    public static final Creator<SearchTagAPI> CREATOR = new Creator<SearchTagAPI>() {
        @Override
        public SearchTagAPI createFromParcel(Parcel in) {
            return new SearchTagAPI(in);
        }

        @Override
        public SearchTagAPI[] newArray(int size) {
            return new SearchTagAPI[size];
        }
    };
}
