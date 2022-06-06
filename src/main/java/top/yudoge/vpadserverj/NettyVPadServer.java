package top.yudoge.vpadserverj;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import top.yudoge.vpadserverj.exceptions.CannotRestartVPadServerException;
import top.yudoge.vpadserverj.exceptions.DuplicateMessageHandlerException;
import top.yudoge.vpadserverj.exceptions.MessageHandlerNotFoundException;
import top.yudoge.vpadserverj.exceptions.VPadServerStartFaildException;
import top.yudoge.vpadserverj.handlers.SignedNumberLengthFieldBasedFrameDecoder;
import top.yudoge.vpadserverj.handlers.VPadMessageDecoder;
import top.yudoge.vpadserverj.handlers.VPadMessageEncoder;
import top.yudoge.vpadserverj.handlers.VPadServerHandler;
import top.yudoge.vpadserverj.messagehandler.HashMapMessageHandlerRegistry;
import top.yudoge.vpadserverj.messagehandler.MessageHandler;
import top.yudoge.vpadserverj.messagehandler.MessageHandlerRegistry;
import top.yudoge.vpadserverj.midi.MidiDeviceHolder;

import java.util.*;
import java.util.function.Consumer;

public class NettyVPadServer implements VPadServer {

    private final MidiDeviceHolder midiDeviceHolder;

    private ServerBootstrap bootstrap;
    // 用来accept客户端连接的事件循环
    private EventLoopGroup bossEventGroup;
    // 用来处理IO传输的事件循环
    private EventLoopGroup workerEventGroup;

    private ChannelFuture channelFuture;

    private volatile boolean isStarted = false;

    private Object startLock = new Object();

    private MessageHandlerRegistry handlerRegistry;

    private List<Consumer<VPadServer>> serverStartCallbacks;
    private List<Consumer<VPadServer>> serverStopCallbacks;

    public NettyVPadServer(MidiDeviceHolder holder) {
        this.handlerRegistry = new HashMapMessageHandlerRegistry();
        this.midiDeviceHolder = holder;
        this.serverStartCallbacks = new LinkedList<>();
        this.serverStopCallbacks = new LinkedList<>();
    }

    @Override
    public void start() {
        try {
            synchronized (startLock) {
                if (isStarted()) throw new CannotRestartVPadServerException("VPadServer is bound to 0.0.0.0:1236. Cannot bind again!");
                bossEventGroup = new NioEventLoopGroup();
                workerEventGroup = new NioEventLoopGroup();
                bootstrap = new ServerBootstrap()
                        .channel(NioServerSocketChannel.class)
                        .group(bossEventGroup, workerEventGroup)
                        .option(ChannelOption.SO_BACKLOG, 10)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel sc) throws Exception {
                                sc.pipeline()
                                        .addLast(new SignedNumberLengthFieldBasedFrameDecoder(Constants.MAX_MESSAGE_BYTES, 0, 2, 0 ,2))
                                        .addLast(new VPadMessageDecoder())
                                        .addLast(new LengthFieldPrepender(2))
                                        .addLast(new VPadMessageEncoder())
                                        .addLast(new VPadServerHandler(NettyVPadServer.this));
                            }
                        });
                channelFuture = bootstrap.bind("0.0.0.0", Constants.DEFAULT_PORT);
                channelFuture.sync();
                isStarted = true;
                invokeAllStartCallback();
            }

            // 阻塞
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new VPadServerStartFaildException("Cannot start vpad server", e);
        } finally {
            bossEventGroup.shutdownGracefully();
            workerEventGroup.shutdownGracefully();
            bootstrap = null;
            bossEventGroup = null;
            workerEventGroup = null;
            channelFuture = null;
        }
    }

    private void invokeAllStartCallback() {
        serverStartCallbacks.forEach(cb -> cb.accept(this));
    }
    private void invokeAllStopCallback() {
        serverStopCallbacks.forEach(cb -> cb.accept(this));
    }

    @Override
    public void stop() {
        synchronized (startLock) {
            if (channelFuture == null) throw new IllegalStateException("Server is not started yet! So you can stop it!");
            channelFuture.channel().close();
            isStarted = false;
            invokeAllStopCallback();
        }
    }

    @Override
    public boolean isStarted() {
        return isStarted;
    }

    @Override
    public int connections() {
        return -1;
    }

    @Override
    public synchronized VPadServer addServerStartCallback(Consumer<VPadServer> callback) {
        if (callback == null) throw new IllegalArgumentException("callback cannot be null");
        serverStartCallbacks.add(callback);
        return this;
    }

    @Override
    public synchronized VPadServer addServerStopCallback(Consumer<VPadServer> callback) {
        if (callback == null) throw new IllegalArgumentException("callback cannot be null");
        serverStopCallbacks.add(callback);
        return this;
    }

    @Override
    public MessageHandlerRegistry messageHandlerRegistry() {
        return handlerRegistry;
    }

    @Override
    public MidiDeviceHolder midiDeviceHolder() {
        return midiDeviceHolder;
    }

}
