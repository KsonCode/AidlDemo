package com.bwie.server;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    private String name;

    protected Person(Parcel in) {
        name = in.readString();
    }
    public Person(String name){
        this.name = name;
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public void readFromParcel(Parcel dest) {
        name = dest.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
