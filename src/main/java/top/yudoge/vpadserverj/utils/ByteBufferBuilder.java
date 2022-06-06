package top.yudoge.vpadserverj.utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.function.Function;

/**
 * 用于快速创建一个ByteBuffer的类
 * 线程不安全，也不应该在多个线程中并发使用
 */
public interface ByteBufferBuilder {
    /**
     * 下面的putXXX方法，向ByteBuffer中添加内容并修改position
     * 如果空间不足会抛出BufferOverflowException
     */
    ByteBufferBuilder putBytes(byte[] bytes);
    ByteBufferBuilder putBytes(byte[] bytes, int offset, int len);
    ByteBufferBuilder putByte(byte num);
    ByteBufferBuilder putShort(short num);
    ByteBufferBuilder putInt(int num);
    ByteBufferBuilder putLong(long num);
    ByteBufferBuilder putString(String string, Charset charset);
    /**
     * 这个需要说明一下，一般情况下，将一个对象或什么的进行二进制编码，在一些可变长度的字段上
     * 需要通过在前面添加一个长度来确定后面的字符串有多长，否则解码的一端不会知道哪里结束
     * @param string 要编码的字符串
     * @param charset 字符集
     * @param byteDepth 使用几个字节记录字符串长度，比如如果使用一个字节，那么字符串编码后所占的位数必须小等于127
     *                  byteDepth in (1, 2, 4)，如果它不在这个集合中，将抛出IllegalArgumentException
     *                  如果要编码的字符串长度大于给定位数能表示的有符号整数最大大小，则抛出StringTooLongException
     *
     * @return this
     */
    ByteBufferBuilder putStringWithLength(String string, Charset charset, int byteDepth);
    ByteBufferBuilder putChar(char c);
    ByteBufferBuilder putObject(Object o, Function<Object, byte[]> converter);

    /**
     * build方法创建ByteBuffer，新建的ByteBuffer的position是0
     * limit是之前存放的内容的最后位置（请注意，不是你new时指定的len）
     * 也就是说，在一个实现类返回ByteBuffer之前，ByteBuffer需要转换成读模式：
     * (也就是说，在一个实现类返回ByteBuffer之前，要先执行下面的代码)
     *   buffer.flip()
     *
     * capacity是构造Builder时指定的大小，关于capacity的值只是一个建议，具体看实现类
     *
     *
     * 总之，build后的ByteBuffer的remaining区域就是你写入的所有内容
     *
     * build之后putXXX方法不可用，调用抛出IllegalStateException
     * build方法不可重复调用，重复调用抛出IllegalStateException
     * @return 创建的ByteBuffer
     */
    ByteBuffer build();

    /**
     * 先使用build方法得到ByteBuffer，然后将它的remaining中的数据返回成byte数组
     * 注意，调用该方法的副作用和调用build一致
     * @return ByteBuffer的remaining区域代表的byte数组
     */
    byte[] toBytes();
}

