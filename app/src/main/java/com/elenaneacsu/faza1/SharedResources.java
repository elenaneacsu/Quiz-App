package com.elenaneacsu.faza1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SharedResources implements Parcelable {

    private String author;
    private String subject;
    private String chapter;
    private String requiredTime;
    private String quizType;
    private ArrayList<Question> questions;

    public SharedResources(String author, String subject, String chapter, String requiredTime, String quizType, ArrayList<Question> questions) {
        this.author = author;
        this.subject = subject;
        this.chapter = chapter;
        this.requiredTime = requiredTime;
        this.quizType = quizType;
        this.questions = questions;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getRequiredTime() {
        return requiredTime;
    }

    public void setRequiredTime(String requiredTime) {
        this.requiredTime = requiredTime;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "SharedResources{" +
                "author='" + author + '\'' +
                ", subject='" + subject + '\'' +
                ", chapter='" + chapter + '\'' +
                ", requiredTime='" + requiredTime + '\'' +
                ", quizType='" + quizType + '\'' +
                ", questions=" + questions +
                '}';
    }

    protected SharedResources(Parcel in) {
        author = in.readString();
        subject = in.readString();
        chapter = in.readString();
        requiredTime = in.readString();
        quizType = in.readString();
        if (in.readByte() == 0x01) {
            questions = new ArrayList<Question>();
            in.readList(questions, Question.class.getClassLoader());
        } else {
            questions = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(subject);
        dest.writeString(chapter);
        dest.writeString(requiredTime);
        dest.writeString(quizType);
        if (questions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(questions);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SharedResources> CREATOR = new Parcelable.Creator<SharedResources>() {
        @Override
        public SharedResources createFromParcel(Parcel in) {
            return new SharedResources(in);
        }

        @Override
        public SharedResources[] newArray(int size) {
            return new SharedResources[size];
        }
    };
}