package com.exam.resultprocess.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Users implements Parcelable {
    private String fullName = "";
    private String email = "";
    private String gender = "";
    private String identity = "";
    private String type = "";
    private String designationOrCourse = "";
    private String mobile = "";
    private String imageName = "";
    private String extension = "";
    private String password = "";
    private String confirmPassword = "";

    public Users() {

    }

    protected Users(Parcel in) {
        fullName = in.readString();
        email = in.readString();
        gender = in.readString();
        identity = in.readString();
        type = in.readString();
        designationOrCourse = in.readString();
        mobile = in.readString();
        imageName = in.readString();
        extension = in.readString();
        password = in.readString();
        confirmPassword = in.readString();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

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

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesignationOrCourse() {
        return designationOrCourse;
    }

    public void setDesignationOrCourse(String designationOrCourse) {
        this.designationOrCourse = designationOrCourse;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullName);
        dest.writeString(email);
        dest.writeString(gender);
        dest.writeString(identity);
        dest.writeString(type);
        dest.writeString(designationOrCourse);
        dest.writeString(imageName);
        dest.writeString(extension);
        dest.writeString(password);
        dest.writeString(confirmPassword);
    }
}
