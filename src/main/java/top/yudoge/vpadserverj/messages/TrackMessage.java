package top.yudoge.vpadserverj.messages;

import top.yudoge.vpadserverj.utils.ByteBufferReader;
import top.yudoge.vpadserverj.utils.ByteBufferToolsFactory;

import java.nio.ByteBuffer;

public class TrackMessage extends Message {

    private int nth;
    private int state;
    private int value;

    public TrackMessage() {
        super(MessageTypes.TRACKMESSAGE);
    }

    public TrackMessage(int nth, int state, int value) {
        this();
        this.nth = nth;
        this.state = state;
        this.value = value;
    }

    @Override
    public void loadBodyFromByteArray(byte[] bytes) {
        ByteBufferReader reader = ByteBufferToolsFactory.newReader(ByteBuffer.wrap(bytes));
        this.nth = reader.getByte();
        this.state = reader.getByte();
        this.value = reader.getByte();
    }

    @Override
    protected byte[] bodyToByteArray() {
        return ByteBufferToolsFactory.newBuilder(3)
                .putByte((byte) this.nth)
                .putByte((byte) this.state)
                .putByte((byte)this.value)
                .toBytes();
    }

    @Override
    public String toString() {
        return "TrackMessage{" +
                "nth=" + nth +
                ", state=" + state +
                ", value=" + value +
                '}';
    }
}
