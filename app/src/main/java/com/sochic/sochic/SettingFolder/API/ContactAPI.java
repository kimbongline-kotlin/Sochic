package com.sochic.sochic.SettingFolder.API;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContactAPI implements Parcelable {

    public boolean success;
    public List<ContactList> response;

    public static class  ContactList implements Parcelable {
        public String idx;
        public boolean answer_confirm;
        public String title;
        public String nickname;
        public String date;
        public String question;
        public String answer;
        public String product_name;
        public String p_idx;
        public String image;
        public boolean open;


        protected ContactList(Parcel in) {
            idx = in.readString();
            answer_confirm = in.readByte() != 0;
            title = in.readString();
            nickname = in.readString();
            date = in.readString();
            question = in.readString();
            answer = in.readString();
            product_name = in.readString();
            p_idx = in.readString();
            image = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(idx);
            dest.writeByte((byte) (answer_confirm ? 1 : 0));
            dest.writeString(title);
            dest.writeString(nickname);
            dest.writeString(date);
            dest.writeString(question);
            dest.writeString(answer);
            dest.writeString(product_name);
            dest.writeString(p_idx);
            dest.writeString(image);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ContactList> CREATOR = new Creator<ContactList>() {
            @Override
            public ContactList createFromParcel(Parcel in) {
                return new ContactList(in);
            }

            @Override
            public ContactList[] newArray(int size) {
                return new ContactList[size];
            }
        };
    }


    protected ContactAPI(Parcel in) {
        success = in.readByte() != 0;
        response = in.createTypedArrayList(ContactList.CREATOR);
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

    public static final Creator<ContactAPI> CREATOR = new Creator<ContactAPI>() {
        @Override
        public ContactAPI createFromParcel(Parcel in) {
            return new ContactAPI(in);
        }

        @Override
        public ContactAPI[] newArray(int size) {
            return new ContactAPI[size];
        }
    };
}
