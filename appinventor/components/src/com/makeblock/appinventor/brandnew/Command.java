package com.makeblock.appinventor.brandnew;

import java.util.Arrays;

public class Command {

    private static final String TAG = Command.class.getSimpleName();
    public byte[] bytes;
    public int priorityLevel;

    @Override
    public String toString() {
        return "Command{" +
                "bytes=" + Arrays.toString(bytes) +
                '}';
    }

    public Command(Builder builder) {
        this.bytes = builder.command;
        this.priorityLevel = builder.priorityLevel;
    }

    public static class Builder {
        private byte[] command = new byte[0];
        private int priorityLevel;

        public Builder appendCommand(byte[] bytes) {
            this.command = joinBytes(command, bytes);
            return this;
        }

        public Builder setPriority(int priorityLevel) {
            this.priorityLevel = priorityLevel;
            return this;
        }

        private byte[] joinBytes(byte[] a, byte[] b) {
            byte[] c = new byte[a.length + b.length];
            System.arraycopy(a, 0, c, 0, a.length);
            System.arraycopy(b, 0, c, a.length, b.length);
            return c;
        }

        public Command build() {
            return new Command(this);
        }
    }
}
