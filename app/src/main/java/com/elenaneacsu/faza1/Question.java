package com.elenaneacsu.faza1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Question implements Parcelable {
    private String time;
    private String text;
    private List<String> response;


    public Question(String time, String text, List<String> response) {
        this.time = time;
        this.text = text;
        this.response = response;
    }

    @Override
    public String toString() {
        return "Question{" +
                "time='" + time + '\'' +
                ", text='" + text + '\'' +
                ", response=" + response +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getResponse() {
        return response;
    }

    public void setResponse(List<String> response) {
        this.response = response;
    }

    protected Question(Parcel in) {
        time = in.readString();
        text = in.readString();
        if (in.readByte() == 0x01) {
            response = new ArrayList<String>();
            in.readList(response, String.class.getClassLoader());
        } else {
            response = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(time);
        dest.writeString(text);
        if (response == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(response);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
