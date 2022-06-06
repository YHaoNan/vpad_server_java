package top.yudoge.vpadserverj.messages;

import top.yudoge.vpadserverj.utils.ByteBufferReader;
import top.yudoge.vpadserverj.utils.ByteBufferToolsFactory;

import java.nio.ByteBuffer;

// length : 2
public class CCMessage extends Message {
    private int channel;
    private int value;

    public CCMessage() {
        super(MessageTypes.CCEVENT);
    }

    public CCMessage(int channel, int value) {
        this();
        this.channel = channel;
        this.value = value;
    }

    @Override
    public void loadBodyFromByteArray(byte[] bytes) {
        ByteBufferReader reader = ByteBufferToolsFactory.newReader(ByteBuffer.wrap(bytes));
        this.channel = reader.getByte();
        this.value = reader.getByte();
    }

    @Override
    protected byte[] bodyToByteArray() {
        return ByteBufferToolsFactory.newBuilder(2)
                .putByte((byte) this.channel)
                .putByte((byte) this.value)
                .toBytes();
    }

    @Override
    public String toString() {
        return "CCMessage{" +
                "channel=" + channel +
                ", value=" + value +
                '}';
    }
}
