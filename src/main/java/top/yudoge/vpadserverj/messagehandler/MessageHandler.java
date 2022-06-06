package top.yudoge.vpadserverj.messagehandler;

import top.yudoge.vpadserverj.messages.Message;

/**
 * MessageHandler是top.yudoge.vpadserverj.message.Message的子类消息的处理器
 * 它并未耦合到任何服务端实现方式中
 *
 * MessageHandler是并发不安全的，所以你不应该在没有同步机制的情况下在MessageHandler类中保存任何状态
 * 有些MessageHandler必须保存状态，如ArpMessageHandler，请这样的实现类确保具有完备的并发安全机制
 * @param <T> 它能够处理的Message
 */
public interface MessageHandler<T extends Message> {

    /**
     * 处理方法，如果该MessageHandler被注册到服务器中（通过MessageHandlerRegistry）
     * 并且服务器接到了该Handler可以处理的类型的Message实例，那么该方法会被调用
     *
     * handle消息代表一次与客户端的服务，这个客户端不一定是同一个，实际上，建议整个应用程序的
     * 生命周期中共用同一个Singleton的MessageHandler
     *
     * handle方法可以由于任何错误抛出任何类型异常，最终它们都会被VPadServerHandler所捕获并正确处理
     *
     * @param message 消息实例
     * @param ctx MessageHandler上下文对象，其中包含服务器配置、本次写回消息所需要的客户端连接，midi设备持有者等
     *
     * @之前的param communicator 是代表和客户端的一个连接，可以通过communicator.send(Message)方法向客户端发送一个Message对象
     *                     ClientEndCommunicator类的存在是为了使MessageHandler不与任何服务端实现耦合
     *                     如果没有这个类，当服务端使用Netty实现，handle方法就需要接收一个ChannelHandlerContext
     *                     当使用传统的BIO Socket实现，handle方法就需要接收一个OutputStream
     *
     *                     ClientEndCommunicator类给所有这些实现方式中的客户端连接提供了一个统一的操作界面
     *
     *                     使用ctx.clientEndCommunicator()代替
     *
     */
    void handle(T message, MessageHandlerContext ctx) throws Exception;

    /**
     * 可以处理的消息的类型，都在Operations类中定义
     * @return
     */
    int messageTypeCanHandle();

}
