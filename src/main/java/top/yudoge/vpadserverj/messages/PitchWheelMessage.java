package top.yudoge.vpadserverj.messages;

import top.yudoge.vpadserverj.utils.ByteBufferReader;
import top.yudoge.vpadserverj.utils.ByteBufferToolsFactory;

import java.nio.ByteBuffer;

public class PitchWheelMessage extends Message {

    private int pos;
    private int prevPos;

    public PitchWheelMessage()
    {
        super(MessageTypes.PITCHWHEELMESSAGE);
    }

    public PitchWheelMessage(int pos, int prevPos) {
        this();
        this.pos = pos;
        this.prevPos = prevPos;
    }

    @Override
    public void loadBodyFromByteArray(byte[] bytes) {
        ByteBufferReader reader = ByteBufferToolsFactory.newReader(ByteBuffer.wrap(bytes));
        this.pos = reader.getByte();
        this.prevPos = reader.getByte();
    }

    @Override
    protected byte[] bodyToByteArray() {
        return ByteBufferToolsFactory.newBuilder(2)
                .putByte((byte) this.pos)
                .putByte((byte) this.prevPos)
                .toBytes();
    }

    @Override
    public String toString() {
        return "PitchWheelMessage{" +
                "pos=" + pos +
                ", prevPos=" + prevPos +
                '}';
    }
}
