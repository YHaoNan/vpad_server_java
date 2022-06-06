package top.yudoge.vpadserverj.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import top.yudoge.vpadserverj.exceptions.UnknownMessageTypeException;
import top.yudoge.vpadserverj.messages.*;
import top.yudoge.vpadserverj.utils.ByteBufferReader;
import top.yudoge.vpadserverj.utils.ByteBufferToolsFactory;

import java.util.List;

public class VPadMessageDecoder extends ByteToMessageDecoder {

    /**
     * 拿掉第一个op，并且根据op来创建对应类型的消息，调用loadBodyFromBytes
     * @param channelHandlerContext
     * @param byteBuf
     * @param list
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        ByteBufferReader reader = ByteBufferToolsFactory.newReader(byteBuf.nioBuffer());
        int op = reader.getByte();
        Message message = switch (op) {
            case MessageTypes.HANDSHAKE -> new HandShakeMessage();
            case MessageTypes.MIDIMESSAGE -> new MidiMessage();
            case MessageTypes.ARPMESSAGE -> new ArpMessage();
            case MessageTypes.CCEVENT -> new CCMessage();
            case MessageTypes.PITCHWHEELMESSAGE -> new PitchWheelMessage();
            case MessageTypes.TRACKMESSAGE -> new TrackMessage();
            case MessageTypes.CONTROLMSG -> new ControlMessage();
            default ->
                throw new UnknownMessageTypeException("Unknown message type : " + op);
        };

        message.loadBodyFromByteArray(reader.getBytes());
        byteBuf.readerIndex(byteBuf.readerIndex() + byteBuf.readableBytes());
        list.add(message);
    }
}
