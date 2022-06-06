package top.yudoge.vpadserverj.utils;

import java.nio.charset.Charset;

public interface ByteBufferReader {
    byte[] getBytes();
    byte[] getBytes(int offset, int len);
//    /**
//     * 以绝对位置获取子byte数组，
//     * @param start position + start
//     * @param len
//     * @return
//     */
//    byte[] getSubBytes(int start, int len);
    byte getByte();
    short getShort();
    int getInt();
    long getLong();
    String getString(Charset charset);
    /**
     * 详见ByteBufferBuilder.putString(String string, Charset charset, int byteDepth)
     * @return this
     */
    String getStringWithLength(Charset charset, int byteDepth);
    char getChar();
}
