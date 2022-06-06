package top.yudoge.vpadserverj.messages;

import top.yudoge.vpadserverj.exceptions.MessageBodyToBigException;
import top.yudoge.vpadserverj.utils.ByteBufferReader;
import top.yudoge.vpadserverj.utils.ByteBufferToolsFactory;

import java.nio.ByteBuffer;

public abstract class Message {
    private int type;
    protected Message(int type) { this.type = type; }

    /**
     * 将子类的body bytes加上一个op
     * 如果子类的消息大小超限(>32767)，则抛出MessageBodyToBigException
     * @return op + body bytes
     */
    public byte[] toByteArray() {
        byte[] bodyBytes = bodyToByteArray();
        int totLen = bodyBytes.length + 1;
        if (bodyBytes.length > Short.MAX_VALUE) throw new MessageBodyToBigException("Message length must less than " + Short.MAX_VALUE);

        return ByteBufferToolsFactory.newBuilder(totLen)
                .putByte((byte) this.type)
                .putBytes(bodyBytes)
                .toBytes();
    }

    /**
     * 从bytearray中加载Body
     * 这个bytes已经去掉了头一个字节，也就是op
     * @param bytes
     */
    public abstract void loadBodyFromByteArray(byte[] bytes);
    protected abstract byte[] bodyToByteArray();

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                '}';
    }
}
