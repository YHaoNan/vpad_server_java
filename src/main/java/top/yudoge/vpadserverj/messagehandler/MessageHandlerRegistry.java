package top.yudoge.vpadserverj.messagehandler;

/**
 * MessageHandler注册处，VPadServer实例需要持有一个该类对象的实例（或者是通过实现该接口）
 *
 * 一个MessageHandlerRegistry需要能够保存`MessageHandler`
 * 并在稍后通过消息类型获取`MessageHandler`实例
 */
public interface MessageHandlerRegistry {
    /**
     * 注册一个MessageHandler
     * 如果该handler的messageTypeCanHandle()方法返回的支持消息类型已经存在于该registry中，则抛出DuplicateMessageHandlerException
     * 否则将它加入到注册处中
     * @param handler
     * @return 与被调用实例相同的实例，用于链式调用
     */
    MessageHandlerRegistry registe(MessageHandler handler);

    /**
     * 根据消息类型获取Handler实例
     * 如果指定的消息类型没有一个已经注册的MessageHandler实例，那么抛出MessageHandlerNotFoundException
     * @param messageType
     * @return
     */
    MessageHandler get(int messageType);

}

