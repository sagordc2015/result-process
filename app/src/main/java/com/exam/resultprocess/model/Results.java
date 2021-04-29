package com.exam.resultprocess.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Results implements Parcelable {
    private String studentId = "";
    private String fullName = "";
    private String email = "";
    private String gender = "";
    private String mobile = "";
    private String designationOrCourseName = "";
    private String semester = "";
    private String attendance = "";
    private String assignment = "";
    private String presentation = "";
    private String midTerm = "";
    private String finalMarks = "";
    private String total = "";
    private String cgpa = "";
    private String grade = "";
    private String remarks = "";
    private String createdOrUpdatedTime = "";

    public Results(){

    }

    protected Results(Parcel in) {
        studentId = in.readString();
        fullName = in.readString();
        email = in.readString();
        gender = in.readString();
        mobile = in.readString();
        designationOrCourseName = in.readString();
        semester = in.readString();
        attendance = in.readString();
        assignment = in.readString();
        presentation = in.readString();
        midTerm = in.readString();
        finalMarks = in.readString();
        total = in.readString();
        cgpa = in.readString();
        grade = in.readString();
        remarks = in.readString();
        createdOrUpdatedTime = in.readString();
    }

    public static final Creator<Results> CREATOR = new Creator<Results>() {
        @Override
        public Results createFromParcel(Parcel in) {
            return new Results(in);
        }

        @Override
        public Results[] newArray(int size) {
            return new Results[size];
        }
    };

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDesignationOrCourseName() {
        return designationOrCourseName;
    }

    public void setDesignationOrCourseName(String designationOrCourseName) {
        this.designationOrCourseName = designationOrCourseName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getMidTerm() {
        return midTerm;
    }

    public void setMidTerm(String midTerm) {
        this.midTerm = midTerm;
    }

    public String getFinalMarks() {
        return finalMarks;
    }

    public void setFinalMarks(String finalMarks) {
        this.finalMarks = finalMarks;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        dest.writeString(studentId);
        dest.writeString(fullName);
        dest.writeString(email);
        dest.writeString(gender);
        dest.writeString(mobile);
        dest.writeString(designationOrCourseName);
        dest.writeString(semester);
        dest.writeString(attendance);
        dest.writeString(assignment);
        dest.writeString(presentation);
        dest.writeString(midTerm);
        dest.writeString(finalMarks);
        dest.writeString(total);
        dest.writeString(cgpa);
        dest.writeString(grade);
        dest.writeString(remarks);
        dest.writeString(createdOrUpdatedTime);
    }
}
