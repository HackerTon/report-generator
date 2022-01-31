package com.hackerton.recording.ui.home;

import com.hackerton.recording.Callback;
import com.hackerton.recording.database.DatabaseManager;
import com.hackerton.recording.entity.History;
import com.hackerton.recording.entity.StudentProgress;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter {
    final private Callback mView;
    final private DatabaseManager mDbm;

    public HomePresenter(Callback mView) {
        this.mView = mView;
        this.mDbm = new DatabaseManager();
    }

    public void testWriteDummyHistory() {
        History newHistory = new History();
        ArrayList<StudentProgress> studentProgress = newHistory.getmStudentProgress();
        studentProgress.add(new StudentProgress(10, "studentname",
                "studentLevel", "model",
                "progress"));

        mDbm.writeHistory(newHistory)
                .addOnSuccessListener(runnable -> {
                }).addOnFailureListener(runnable -> {
        });
    }

    public void fetchHistory() {
        mDbm.getAllHistory().addOnSuccessListener(runnable -> {
            List<History> histories = runnable.toObjects(History.class);
            mView.receive(histories);
        }).addOnFailureListener(mView::error);
    }
}
