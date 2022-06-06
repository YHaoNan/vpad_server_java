package top.yudoge.vpadserverj.messages;

import top.yudoge.vpadserverj.utils.ByteBufferReader;
import top.yudoge.vpadserverj.utils.ByteBufferToolsFactory;

import java.nio.ByteBuffer;

public class MidiMessage extends Message {
    public static final int STATE_NOTE_ON = 1;
    public static final int STATE_NOTE_OFF = 0;
    private int note;
    private int velocity;
    private int state;

    public MidiMessage() {
        super(MessageTypes.MIDIMESSAGE);
    }

    public MidiMessage(int note, int velocity, int state) {
        this();
        this.note = note;
        this.velocity = velocity;
        this.state = state;
    }

    @Override
    public void loadBodyFromByteArray(byte[] bytes) {
        ByteBufferReader reader = ByteBufferToolsFactory.newReader(ByteBuffer.wrap(bytes));
        this.note = reader.getByte();
        this.velocity = reader.getByte();
        this.state = reader.getByte();
    }

    public int getNote() {
        return note;
    }

    public int getState() {
        return state;
    }
    public int getVelocity() {
        return velocity;
    }

    @Override
    protected byte[] bodyToByteArray() {
        return ByteBufferToolsFactory.newBuilder(3)
                .putByte((byte)this.note)
                .putByte((byte)this.velocity)
                .putByte((byte)this.state)
                .toBytes();

    }

    @Override
    public String toString() {
        return "MidiMessage{" +
                "note=" + note +
                ", velocity=" + velocity +
                ", state=" + state +
                '}';
    }
}
