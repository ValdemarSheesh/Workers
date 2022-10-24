package com.example.workers.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Entity(tableName = "workers")
public class Worker implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String surname;
    private String birthday;
    private String speciality;
    private String age;

    public Worker(int id, String name, String surname, String birthday, String speciality, String age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.speciality = speciality;
        this.age = age;
    }

    @Ignore
    public Worker(String name, String surname, String birthday, String speciality, String age) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.speciality = speciality;
        this.age = age;
    }

    public static final Creator<Worker> CREATOR = new Creator<Worker>() {
        @Override
        public Worker createFromParcel(Parcel in) {
            String name = in.readString();
            String surname = in.readString();
            String birthday = in.readString();
            String speciality = in.readString();
            String age = in.readString();
            return new Worker(name, surname, birthday, speciality, age);
        }

        @Override
        public Worker[] newArray(int size) {
            return new Worker[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeString(birthday);
        parcel.writeString(speciality);
        parcel.writeString(age);
    }
}


















