package top.yudoge.vpadserverj.utils;

import java.nio.ByteBuffer;

public class ByteBufferToolsFactory {
    public static ByteBufferBuilder newBuilder(int len) {
        return new HeapByteBufferBuilder(len);
    }
    public static ByteBufferReader newReader(ByteBuffer buffer) {
        return new DefaultByteBufferReader(buffer);
    }
}
