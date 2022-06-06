package top.yudoge.vpadserverj.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

public class SignedNumberLengthFieldBasedFrameDecoder extends LengthFieldBasedFrameDecoder {
    public SignedNumberLengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    public SignedNumberLengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    public SignedNumberLengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    public SignedNumberLengthFieldBasedFrameDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
        buf = buf.order(order);
        long frameLength;
        switch(length) {
            case 1:
                frameLength = (long)buf.getByte(offset);
                break;
            case 2:
                frameLength = (long)buf.getShort(offset);
                break;
            case 3:
                frameLength = (long)buf.getMedium(offset);
                break;
            case 4:
                frameLength = buf.getInt(offset);
                break;
            case 5:
            case 6:
            case 7:
            default:
                throw new DecoderException("unsupported lengthFieldLength");
            case 8:
                frameLength = buf.getLong(offset);
        }

        return frameLength;

    }
}
