package com.hackerton.recording.entity;

import androidx.annotation.Keep;

@Keep
public class StudentProgress {
    public int mId;
    public String mStudentName;
    public String mStudentLevel;
    public String mStudentModelName;
    public String mStudentProgress;

    public StudentProgress(int id, String studentName, String studentLevel, String studentModelName, String studentProgress) {
        this.mStudentName = studentName;
        this.mStudentLevel = studentLevel;
        this.mStudentModelName = studentModelName;
        this.mStudentProgress = studentProgress;
    }

    public StudentProgress() {
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmStudentName() {
        return mStudentName;
    }

    public void setmStudentName(String mStudentName) {
        this.mStudentName = mStudentName;
    }

    public String getmStudentLevel() {
        return mStudentLevel;
    }

    public void setmStudentLevel(String mStudentLevel) {
        this.mStudentLevel = mStudentLevel;
    }

    public String getmStudentModelName() {
        return mStudentModelName;
    }

    public void setmStudentModelName(String mStudentModelName) {
        this.mStudentModelName = mStudentModelName;
    }

    public String getmStudentProgress() {
        return mStudentProgress;
    }

    public void setmStudentProgress(String mStudentProgress) {
        this.mStudentProgress = mStudentProgress;
    }
}
