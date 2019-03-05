package com.elenaneacsu.faza1;

import android.os.Parcel;
import android.os.Parcelable;

public class Information implements Parcelable {
    private String title;
    private String fact;

    public Information(String title, String fact) {
        this.title = title;
        this.fact = fact;
    }

    protected Information(Parcel in) {
        title = in.readString();
        fact = in.readString();
    }

    public static final Creator<Information> CREATOR = new Creator<Information>() {
        @Override
        public Information createFromParcel(Parcel in) {
            return new Information(in);
        }

        @Override
        public Information[] newArray(int size) {
            return new Information[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(fact);
    }
}
