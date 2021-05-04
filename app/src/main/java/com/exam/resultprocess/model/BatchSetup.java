package com.exam.resultprocess.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BatchSetup implements Parcelable {

    private String courseName = "";
    private String batchCode = "";
    private String batchName = "";
    private String batchYear = "";
    private String createdOrUpdatedTime = "";

    public BatchSetup(){

    }

    protected BatchSetup(Parcel in) {
        courseName = in.readString();
        batchCode = in.readString();
        batchName = in.readString();
        batchYear = in.readString();
        createdOrUpdatedTime = in.readString();
    }

    public static final Creator<BatchSetup> CREATOR = new Creator<BatchSetup>() {
        @Override
        public BatchSetup createFromParcel(Parcel in) {
            return new BatchSetup(in);
        }

        @Override
        public BatchSetup[] newArray(int size) {
            return new BatchSetup[size];
        }
    };

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getBatchYear() {
        return batchYear;
    }

    public void setBatchYear(String batchYear) {
        this.batchYear = batchYear;
    }

    public String getCreatedOrUpdatedTime() {
        return createdOrUpdatedTime;
    }

    public void setCreatedOrUpdatedTime(String createdOrUpdatedTime) {
        this.createdOrUpdatedTime = createdOrUpdatedTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(courseName);
        dest.writeString(batchCode);
        dest.writeString(batchName);
        dest.writeString(batchYear);
        dest.writeString(createdOrUpdatedTime);
    }
}
