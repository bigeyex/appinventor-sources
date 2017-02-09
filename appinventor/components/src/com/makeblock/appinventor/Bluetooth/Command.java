package com.google.appinventor.components.runtime.Bluetooth;

import java.util.Arrays;

public class Command {

    private static final String TAG = Command.class.getSimpleName();
    public byte[] bytes;
    public int priorityLevel;

    public Command(byte[] bytes) {
        this(bytes, 1);
    }

    public Command(byte[] bytes, int priorityLevel) {
        this.bytes = bytes;
        this.priorityLevel = priorityLevel;
    }

    @Override
    public String toString() {
        return "Command{" +
                "bytes=" + Arrays.toString(bytes) +
                '}';
    }
}
