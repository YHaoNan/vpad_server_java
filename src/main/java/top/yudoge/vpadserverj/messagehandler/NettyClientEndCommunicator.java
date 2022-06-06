package top.yudoge.vpadserverj.messagehandler;

import io.netty.channel.ChannelHandlerContext;
import top.yudoge.vpadserverj.messages.Message;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class NettyClientEndCommunicator implements ClientEndCommunicator {

    private final ChannelHandlerContext ctx;

    public NettyClientEndCommunicator(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void send(Message message) {
        ctx.writeAndFlush(message);
    }

    @Override
    public String clientHostName() {
        return ((InetSocketAddress)ctx.channel().remoteAddress()).getHostString();
    }

    @Override
    public int clientPort() {
        return ((InetSocketAddress)ctx.channel().remoteAddress()).getPort();
    }

}
