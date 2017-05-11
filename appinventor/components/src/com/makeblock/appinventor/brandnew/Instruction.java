package com.makeblock.appinventor.brandnew;

import java.util.Arrays;

/**
 * Created by xuexin on 2017/3/31.
 */

public abstract class Instruction {
    public abstract byte[] getBytes();

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        byte[] bytes;
        if (obj instanceof Instruction) {
            bytes = ((Instruction) obj).getBytes();
        } else if (obj instanceof byte[]) {
            bytes = (byte[]) obj;
        } else {
            return false;
        }
        return Arrays.equals(getBytes(), bytes);
    }

}
