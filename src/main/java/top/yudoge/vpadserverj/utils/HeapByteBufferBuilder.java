package top.yudoge.vpadserverj.utils;


import top.yudoge.vpadserverj.Constants;
import top.yudoge.vpadserverj.exceptions.StringTooLongException;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.function.Function;

public class HeapByteBufferBuilder implements ByteBufferBuilder {
    private final ByteBuffer buffer;
    private boolean isBuilded = false;
    public HeapByteBufferBuilder(int len) {
        buffer = ByteBuffer.allocate(len);
    }

    private void requireNotBuilded() {
        if (isBuilded) throw new IllegalStateException("Cannot call this method after builded!");
    }

    @Override
    public ByteBufferBuilder putBytes(byte[] bytes) {
        requireNotBuilded();
        buffer.put(bytes);
        return this;
    }

    @Override
    public ByteBufferBuilder putBytes(byte[] bytes, int offset, int len) {
        requireNotBuilded();
        buffer.put(bytes, offset, len);
        return this;
    }

    @Override
    public ByteBufferBuilder putByte(byte num) {
        requireNotBuilded();
        buffer.put(num);
        return this;
    }

    @Override
    public ByteBufferBuilder putShort(short num) {
        requireNotBuilded();
        buffer.putShort(num);
        return this;
    }

    @Override
    public ByteBufferBuilder putInt(int num) {
        requireNotBuilded();
        buffer.putInt(num);
        return this;
    }

    @Override
    public ByteBufferBuilder putLong(long num) {
        requireNotBuilded();
        buffer.putLong(num);
        return this;
    }

    @Override
    public ByteBufferBuilder putString(String string, Charset charset) {
        requireNotBuilded();
        return putBytes(string.getBytes(charset));
    }

    private static final Integer[] BYTEDEPTH_ARR = new Integer[] {1,2,4};

    @Override
    public ByteBufferBuilder putStringWithLength(String string, Charset charset, int byteDepth) {
        requireNotBuilded();

        if (!ArrayUtils.in(byteDepth, BYTEDEPTH_ARR)) throw new IllegalArgumentException("byteDepth must in (1,2,4,8), but got " + byteDepth);

        int maxStringLen = ((int)Math.pow(2, byteDepth * 8)) / 2 - 1;

        if (string.length() > maxStringLen) throw new StringTooLongException(
                "Max string length is " + maxStringLen + " when you use byteDepth = " + byteDepth + ", but your string length is " + string.length()
        );

        byte[] bytes = string.getBytes(Constants.DEFAULT_CHARSET);

        if (byteDepth == 1) {
            buffer.put((byte)bytes.length);
        } else if (byteDepth == 2) {
            buffer.putShort((short) bytes.length);
        } else {
            buffer.putInt(bytes.length);
        }

        return putBytes(bytes);
    }

    @Override
    public ByteBufferBuilder putChar(char c) {
        requireNotBuilded();
        buffer.putChar(c);
        return this;
    }

    @Override
    public ByteBufferBuilder putObject(Object o, Function<Object, byte[]> converter) {
        return putBytes(
                converter.apply(o)
        );
    }

    @Override
    public ByteBuffer build() {
        requireNotBuilded();
        buffer.flip();
        isBuilded = true;
        return buffer;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer byteBuffer = build();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        return bytes;
    }
}
