package com.hackerton.recording.ui.home;

public class History {
    final String id;
    final String date;
    final String history;

    public History(String id, String date, String history) {
        this.id = id;
        this.date = date;
        this.history = history;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getHistory() {
        return history;
    }
}
