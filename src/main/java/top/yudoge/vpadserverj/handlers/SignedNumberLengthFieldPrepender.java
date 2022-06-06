package top.yudoge.vpadserverj.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldPrepender;

import java.nio.ByteOrder;
import java.util.List;

public class SignedNumberLengthFieldPrepender extends LengthFieldPrepender {
    public SignedNumberLengthFieldPrepender(int lengthFieldLength) {
        super(lengthFieldLength);
    }

    public SignedNumberLengthFieldPrepender(int lengthFieldLength, boolean lengthIncludesLengthFieldLength) {
        super(lengthFieldLength, lengthIncludesLengthFieldLength);
    }

    public SignedNumberLengthFieldPrepender(int lengthFieldLength, int lengthAdjustment) {
        super(lengthFieldLength, lengthAdjustment);
    }

    public SignedNumberLengthFieldPrepender(int lengthFieldLength, int lengthAdjustment, boolean lengthIncludesLengthFieldLength) {
        super(lengthFieldLength, lengthAdjustment, lengthIncludesLengthFieldLength);
    }

    public SignedNumberLengthFieldPrepender(ByteOrder byteOrder, int lengthFieldLength, int lengthAdjustment, boolean lengthIncludesLengthFieldLength) {
        super(byteOrder, lengthFieldLength, lengthAdjustment, lengthIncludesLengthFieldLength);
    }

    @Override
    public void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        super.encode(ctx, msg, out);
    }
}
