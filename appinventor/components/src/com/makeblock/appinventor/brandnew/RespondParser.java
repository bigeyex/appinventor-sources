package com.makeblock.appinventor.brandnew;

/**
 * Created by xuexin on 2017/4/13.
 */

public abstract class RespondParser {

    private final byte[] buffer = new byte[1000];
    private int length = 0;
    private final byte[] head;
    private final byte[] tail;

    protected RespondParser(byte[] head, byte[] tail) {
        this.head = head;
        this.tail = tail;
    }


    public final void parseBytes(byte[] bytes) {
        int index = length;
        length += bytes.length;
        if (length > buffer.length) {
            throw new RuntimeException("缓冲区大小不够,真的假的");
        }
        System.arraycopy(bytes, 0, buffer, index, bytes.length);
        parseData();
    }


    private int findHeadIndex() {
        for (int i = 0; i <= length - head.length; ++i) {
            boolean equal = true;
            for (int j = 0; j < head.length; ++j) {
                if (buffer[i + j] != head[j]) {
                    equal = false;
                    break;
                }
            }
            if (equal) {
                return i;
            } else {
                continue;
            }
        }
        return -1;
    }

    private int findTailIndex() {
        for (int i = head.length; i <= length - tail.length; ++i) {
            boolean equal = true;
            for (int j = 0; j < tail.length; ++j) {
                if (buffer[i + j] != tail[j]) {
                    equal = false;
                    break;
                }
            }
            if (equal) {
                return i;
            } else {
                continue;
            }
        }
        return -1;
    }

    private void parseData() {
        if (length < head.length + tail.length) {
            return;
        }
        //lyh 按理说不会出现这种硬件完全乱发数据的情况,以防万一
        if (length > buffer.length * 2 / 3) {
            extractBytes(buffer.length / 2);
        }
        int headIndex = findHeadIndex();
        if (headIndex > 0) {
            extractBytes(headIndex);
            parseData();
        } else if (headIndex < 0) {
            return;
        } else {
            int tailIndex = findTailIndex();
            if (tailIndex > 0) {
                byte[] data = extractBytes(tailIndex + tail.length);
                packData(data);
                parseData();
            }
        }
    }


    /**
     * 提取指定长度的数据(会删除)
     *
     * @param length
     * @return
     */
    private byte[] extractBytes(int length) {
        byte[] bytes = new byte[length];
        System.arraycopy(buffer, 0, bytes, 0, length);
        System.arraycopy(buffer, length, buffer, 0, this.length - length);
        this.length -= length;
        return bytes;
    }

    protected abstract void packData(byte[] bytes);
}
