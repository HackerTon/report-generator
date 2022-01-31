package com.hackerton.recording.entity;

import androidx.annotation.Keep;

@Keep
public class Student {
    public String mClassday;
    public int mIndex;
    public String mName;
    public String mProgress;

    public Student(String classDay, int index, String name, String progress) {
        mClassday = classDay;
        mIndex = index;
        mName = name;
        mProgress = progress;
    }

    public Student() {
    }

}
