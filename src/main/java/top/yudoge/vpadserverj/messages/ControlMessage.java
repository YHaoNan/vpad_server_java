package top.yudoge.vpadserverj.messages;

import top.yudoge.vpadserverj.utils.ByteBufferReader;
import top.yudoge.vpadserverj.utils.ByteBufferToolsFactory;

import java.nio.ByteBuffer;

public class ControlMessage extends Message {
    private int operation;
    private int state;
    private int autoClose;

    public ControlMessage() {
        super(MessageTypes.CONTROLMSG);
    }

    public ControlMessage(int operation, int state, int autoClose) {
        this();
        this.operation = operation;
        this.state = state;
        this.autoClose = autoClose;
    }

    @Override
    public void loadBodyFromByteArray(byte[] bytes) {
        ByteBufferReader reader = ByteBufferToolsFactory.newReader(ByteBuffer.wrap(bytes));
        this.operation = reader.getByte();
        this.state = reader.getByte();
        this.autoClose = reader.getByte();
    }

    @Override
    protected byte[] bodyToByteArray() {
        return ByteBufferToolsFactory.newBuilder(3)
                .putByte((byte) this.operation)
                .putByte((byte) this.state)
                .putByte((byte) this.autoClose)
                .toBytes();
    }

    @Override
    public String toString() {
        return "ControlMessage{" +
                "operation=" + operation +
                ", state=" + state +
                ", autoClose=" + autoClose +
                '}';
    }
}
