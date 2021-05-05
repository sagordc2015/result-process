package com.exam.resultprocess.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TeacherSetup implements Parcelable {
    private String teacherId = "";
    private String teacherName = "";
    private String designation = "";
    private String batchCode = "";
    private String courseName = "";
    private String subjectCode = "";
    private String subjectName = "";
    private String semester = "";
    private String createdOrUpdatedTime = "";

    public TeacherSetup(){

    }

    protected TeacherSetup(Parcel in) {
        teacherId = in.readString();
        teacherName = in.readString();
        designation = in.readString();
        batchCode = in.readString();
        courseName = in.readString();
        subjectCode = in.readString();
        subjectName = in.readString();
        semester = in.readString();
        createdOrUpdatedTime = in.readString();
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCreatedOrUpdatedTime() {
        return createdOrUpdatedTime;
    }

    public void setCreatedOrUpdatedTime(String createdOrUpdatedTime) {
        this.createdOrUpdatedTime = createdOrUpdatedTime;
    }

    public static Creator<TeacherSetup> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<TeacherSetup> CREATOR = new Creator<TeacherSetup>() {
        @Override
        public TeacherSetup createFromParcel(Parcel in) {
            return new TeacherSetup(in);
        }

        @Override
        public TeacherSetup[] newArray(int size) {
            return new TeacherSetup[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(teacherId);
        dest.writeString(teacherName);
        dest.writeString(designation);
        dest.writeString(batchCode);
        dest.writeString(courseName);
        dest.writeString(subjectCode);
        dest.writeString(subjectName);
        dest.writeString(semester);
        dest.writeString(createdOrUpdatedTime);
    }
}
