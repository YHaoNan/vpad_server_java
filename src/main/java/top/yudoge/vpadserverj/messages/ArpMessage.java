package top.yudoge.vpadserverj.messages;

import top.yudoge.vpadserverj.utils.ByteBufferReader;
import top.yudoge.vpadserverj.utils.ByteBufferToolsFactory;

import java.nio.ByteBuffer;

// length : 12
public class ArpMessage extends Message {
    private int note;
    private int velocity;
    private int state;
    private int method;
    private int rate;
    private int swingPct;
    private int upNoteCnt;
    private int velocityAutomation;
    private int dynamicPct;
    private int bpm;

    public ArpMessage() {
        super(MessageTypes.ARPMESSAGE);
    }

    public ArpMessage(int note, int velocity, int state, int method, int rate, int swingPct, int upNoteCnt,
                      int velocityAutomation, int dynamicPct, int bpm) {
        this();
        init(note, velocity, state, method, rate, swingPct, upNoteCnt, velocityAutomation, dynamicPct, bpm);
    }

    private void init(int note, int velocity, int state, int method, int rate, int swingPct, int upNoteCnt,
                      int velocityAutomation, int dynamicPct, int bpm) {
        this.note = note;
        this.velocity = velocity;
        this.state = state;
        this.method = method;
        this.rate = rate;
        this.swingPct = swingPct;
        this.upNoteCnt = upNoteCnt;
        this.velocityAutomation = velocityAutomation;
        this.dynamicPct = dynamicPct;
        this.bpm = bpm;
    }

    @Override
    public void loadBodyFromByteArray(byte[] bytes) {
        ByteBufferReader reader = ByteBufferToolsFactory.newReader(ByteBuffer.wrap(bytes));
        init(
                reader.getByte(), reader.getByte(), reader.getByte(),
                reader.getByte(), reader.getByte(), reader.getByte(), reader.getByte(),
                reader.getByte(), reader.getShort(), reader.getShort()
        );
    }

    @Override
    protected byte[] bodyToByteArray() {
        return ByteBufferToolsFactory.newBuilder(12)
                .putByte((byte) this.note)
                .putByte((byte) this.velocity)
                .putByte((byte) this.state)
                .putByte((byte) this.method)
                .putByte((byte) this.rate)
                .putByte((byte) this.swingPct)
                .putByte((byte) this.upNoteCnt)
                .putByte((byte) this.velocityAutomation)
                .putShort((short) this.dynamicPct)
                .putShort((short) this.bpm).toBytes();
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getSwingPct() {
        return swingPct;
    }

    public void setSwingPct(int swingPct) {
        this.swingPct = swingPct;
    }

    public int getUpNoteCnt() {
        return upNoteCnt;
    }

    public void setUpNoteCnt(int upNoteCnt) {
        this.upNoteCnt = upNoteCnt;
    }

    public int getVelocityAutomation() {
        return velocityAutomation;
    }

    public void setVelocityAutomation(int velocityAutomation) {
        this.velocityAutomation = velocityAutomation;
    }

    public int getDynamicPct() {
        return dynamicPct;
    }

    public void setDynamicPct(int dynamicPct) {
        this.dynamicPct = dynamicPct;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    @Override
    public String toString() {
        return "ArpMessage{" +
                "note=" + note +
                ", velocity=" + velocity +
                ", state=" + state +
                ", method=" + method +
                ", rate=" + rate +
                ", swingPct=" + swingPct +
                ", upNoteCnt=" + upNoteCnt +
                ", velocityAutomation=" + velocityAutomation +
                ", dynamicPct=" + dynamicPct +
                ", bpm=" + bpm +
                '}';
    }


    public static final int STATE_OFF = 0;
    public static final int STATE_ON = 1;

    public static final int METHOD_NO_METHOD = 0;
    public static final int METHOD_UP = 1;
    public static final int METHOD_DOWN = 2;
    public static final int METHOD_UP_DOWN = 3;
    public static final int METHOD_DOWN_UP = 4;
    public static final int METHOD_3CHORD = 5;
    public static final int METHOD_7CHORD = 6;

    public static final int RATE_1_1 = 0;
    public static final int RATE_1_2_D = 1;
    public static final int RATE_1_1_T = 2;
    public static final int RATE_1_2 = 3;
    public static final int RATE_1_4_D = 4;
    public static final int RATE_1_2_T = 5;
    public static final int RATE_1_4 = 6;
    public static final int RATE_1_8_D = 7;
    public static final int RATE_1_4_T = 8;
    public static final int RATE_1_8 = 9;
    public static final int RATE_1_16_D = 10;
    public static final int RATE_1_8_T = 11;
    public static final int RATE_1_16 = 12;
    public static final int RATE_1_32_D = 13;
    public static final int RATE_1_16_T = 14;
    public static final int RATE_1_32 = 15;
    public static final int RATE_1_64_D = 16;
    public static final int RATE_1_32_T = 17;
    public static final int RATE_1_64 = 18;
    public static final int RATE_1_64_T = 19;

    public static final int VELOCITY_NO_AUTOMATION = 0;
    public static final int VELOCITY_UP = 1;
    public static final int VELOCITY_DOWN = 2;
    public static final int VELOCITY_UP_DOWN = 3;
    public static final int VELOCITY_DOWN_UP = 4;
    public static final int VELOCITY_STEP = 5;
    public static final int VELOCITY_RANDOM = 6;

}
