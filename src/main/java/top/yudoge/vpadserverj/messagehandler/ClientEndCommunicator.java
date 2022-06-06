package top.yudoge.vpadserverj.messagehandler;

import top.yudoge.vpadserverj.messages.Message;

import java.net.InetAddress;

/**
 * 代表和客户端的一个连接，可以通过communicator.send(Message)方法向客户端发送一个Message对象
 * ClientEndCommunicator类的存在是为了使MessageHandler不与任何服务端实现耦合
 * 如果没有这个类，当服务端使用Netty实现，handle方法就需要接收一个ChannelHandlerContext
 * 当使用传统的BIO Socket实现，handle方法就需要接收一个OutputStream
 *
 * ClientEndCommunicator类给所有这些实现方式中的客户端连接提供了一个统一的操作界面
 */
public interface ClientEndCommunicator {
    void send(Message message);

    /**
     * 该方法返回可以代表主机名的任意字符串，可以是主机名或ip地址
     * 该方法返回应该尽量快
     * @return
     */
    String clientHostName();
    int clientPort();
}
