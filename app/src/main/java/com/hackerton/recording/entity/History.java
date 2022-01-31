package com.hackerton.recording.entity;

import androidx.annotation.Keep;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Date;

@Keep
public class History {
    public ArrayList<StudentProgress> mStudentProgress;
    @ServerTimestamp
    public Date mTimestamp;

    public History() {
        mStudentProgress = new ArrayList<>();
    }

    public ArrayList<StudentProgress> getmStudentProgress() {
        return mStudentProgress;
    }

    public void setmStudentProgress(ArrayList<StudentProgress> mStudentProgress) {
        this.mStudentProgress = mStudentProgress;
    }

    public Date getmTimestamp() {
        return mTimestamp;
    }

    public void setmTimestamp(Date mTimestamp) {
        this.mTimestamp = mTimestamp;
    }


}