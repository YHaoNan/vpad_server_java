package top.yudoge.vpadserverj.messagehandler;

import top.yudoge.vpadserverj.messages.HandShakeMessage;
import top.yudoge.vpadserverj.messages.MessageTypes;

public class HandShakeMessageHandler implements MessageHandler<HandShakeMessage> {

    @Override
    public void handle(HandShakeMessage message, MessageHandlerContext ctx) {
        ctx.clientEndCommunicator().send(
                new HandShakeMessage("于皓楠的VPadServer", "VPadServer (Java Netty)")
        );
    }

    @Override
    public int messageTypeCanHandle() {
        return MessageTypes.HANDSHAKE;
    }

}
