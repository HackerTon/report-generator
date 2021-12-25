package com.hackerton.recording.ui.home;

public class History {
    final String date;
    final String history;

    public History(String date, String history) {
        this.date = date;
        this.history = history;
    }

    public String getDate() {
        return date;
    }

    public String getHistory() {
        return history;
    }
}
