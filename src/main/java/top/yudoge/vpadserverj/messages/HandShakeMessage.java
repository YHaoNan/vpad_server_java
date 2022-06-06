package top.yudoge.vpadserverj.messages;

import top.yudoge.vpadserverj.Constants;
import top.yudoge.vpadserverj.utils.ByteBufferReader;
import top.yudoge.vpadserverj.utils.ByteBufferToolsFactory;

import java.nio.ByteBuffer;

public class HandShakeMessage extends Message {
    private String name;
    private String platform;

    public HandShakeMessage() {
        super(MessageTypes.HANDSHAKE);
    }
    public HandShakeMessage(String name, String platform) {
        super(MessageTypes.HANDSHAKE);
        this.name = name;
        this.platform = platform;
    }

    public String getName() {
        return name;
    }

    public String getPlatform() {
        return platform;
    }

    @Override
    public void loadBodyFromByteArray(byte[] bytes) {
        ByteBufferReader reader = ByteBufferToolsFactory.newReader(ByteBuffer.wrap(bytes));
        this.name = reader.getStringWithLength(Constants.DEFAULT_CHARSET, 1);
        this.platform = reader.getStringWithLength(Constants.DEFAULT_CHARSET, 1);
    }

    @Override
    protected byte[] bodyToByteArray() {
        return ByteBufferToolsFactory.newBuilder(256)
                .putStringWithLength(this.name, Constants.DEFAULT_CHARSET, 1)
                .putStringWithLength(this.platform, Constants.DEFAULT_CHARSET, 1)
                .toBytes();
    }

    @Override
    public String toString() {
        return "HandShakeMessage{" +
                "name='" + name + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }
}
