package top.yudoge.vpadserverj.utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class DefaultByteBufferReader implements ByteBufferReader {
    private final ByteBuffer buffer;
    protected DefaultByteBufferReader(ByteBuffer buffer) {
        this.buffer = buffer;
    }


    @Override
    public byte[] getBytes() {
        byte[] bytes = new byte[buffer.remaining()];
        this.buffer.get(bytes);
        return bytes;
    }

    @Override
    public byte[] getBytes(int offset, int len) {
        byte[] bytes = new byte[buffer.remaining()];
        this.buffer.get(bytes, offset, len);
        return bytes;
    }

//    @Override
//    public byte[] getSubBytes(int start, int len) {
//
//        return new byte[0];
//    }

    @Override
    public byte getByte() {
        return this.buffer.get();
    }

    @Override
    public short getShort() {
        return this.buffer.getShort();
    }

    @Override
    public int getInt() {
        return this.buffer.getInt();
    }

    @Override
    public long getLong() {
        return this.buffer.getLong();
    }

    private String getFixedLengthStringFromBuffer(Charset charset, int len) {
        byte[] bytes = new byte[len];
        this.buffer.get(bytes);
        return new String(bytes, charset);
    }

    @Override
    public String getString(Charset charset) {
        return getFixedLengthStringFromBuffer(charset, this.buffer.remaining());
    }

    private static final Integer[] BYTEDEPTH_ARR = new Integer[] {1,2,4};
    @Override
    public String getStringWithLength(Charset charset, int byteDepth) {
        if (!ArrayUtils.in(byteDepth, BYTEDEPTH_ARR)) throw new IllegalArgumentException("byteDepth must in (1,2,4,8), but got " + byteDepth);

        int len = 0;
        if (byteDepth == 1) len = getByte();
        else if (byteDepth == 2) len = getShort();
        else len = getInt();

        return getFixedLengthStringFromBuffer(charset, len);
    }

    @Override
    public char getChar() {
        return this.buffer.getChar();
    }
}
