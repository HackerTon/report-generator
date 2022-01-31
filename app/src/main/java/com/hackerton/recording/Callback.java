package com.hackerton.recording;

public interface Callback {
    public void receive(Object response);
    public void error(Object response);
}
