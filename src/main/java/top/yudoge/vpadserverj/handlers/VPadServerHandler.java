package top.yudoge.vpadserverj.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import top.yudoge.vpadserverj.NettyVPadServer;
import top.yudoge.vpadserverj.messagehandler.ClientEndCommunicator;
import top.yudoge.vpadserverj.messagehandler.MessageHandlerContext;
import top.yudoge.vpadserverj.messagehandler.NettyClientEndCommunicator;
import top.yudoge.vpadserverj.messages.Message;
import top.yudoge.vpadserverj.midi.MidiDeviceHolder;

public class VPadServerHandler extends SimpleChannelInboundHandler<Message> {
    private final NettyVPadServer server;

    public VPadServerHandler(NettyVPadServer server) {
        this.server = server;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {
        System.out.println(message);
        try {
            server.messageHandlerRegistry()
                    .get(message.getType())
                    .handle(message, createMessageHandlerContext(channelHandlerContext));
        } catch (Exception e) {
            exceptionCaught(channelHandlerContext, e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private MessageHandlerContext createMessageHandlerContext(ChannelHandlerContext ctx) {
        return new MessageHandlerContext() {
            @Override
            public ClientEndCommunicator clientEndCommunicator() {
                return new NettyClientEndCommunicator(ctx);
            }

            @Override
            public MidiDeviceHolder midiDeviceHolder() {
                return server.midiDeviceHolder();
            }
        };
    }
}
