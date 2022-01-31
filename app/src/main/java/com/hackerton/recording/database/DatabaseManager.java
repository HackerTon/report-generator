package com.hackerton.recording.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.hackerton.recording.entity.History;
import com.hackerton.recording.entity.Student;

public class DatabaseManager {
    final private static String TAG = "DatabaseManager";
    final private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Task<DocumentReference> writeStudent(Student student) {
        return db.collection("student").add(student);
    }

    public Task<DocumentReference> writeHistory(History history) {
        return db.collection("history").add(history);
    }

    public Task<QuerySnapshot> getAllHistory() {
        return db.collection("history").get();
    }
}
